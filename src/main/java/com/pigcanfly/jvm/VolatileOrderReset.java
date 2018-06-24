package com.pigcanfly.jvm;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 指令重排
 * 不加volatile首先线程二获取会有延迟，读的是脏数据
 * 同时会发生指令重排的现象(TODO：代码模拟并未成功)
 */
@Slf4j
public class VolatileOrderReset implements Runnable {
    private  int a = 0;
    private static volatile boolean flag = false;
    private static Map<String, String> value;
    private CountDownLatch countDownLatch;
    private CountDownLatch threadCountDownLatch;
    @Setter
    private Integer execThread;

    public VolatileOrderReset() {
    }

    public VolatileOrderReset(Integer threadId, CountDownLatch countDownLatch, CountDownLatch threadCountDownLatch) {
        this.execThread = threadId;
        this.countDownLatch = countDownLatch;
        this.threadCountDownLatch = threadCountDownLatch;
    }


    @Override
    public void run() {
        if (execThread == Exec.THREAD_A.getThreadId()) {
            try {
                countDownLatch.await();
                threadAAction();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                threadCountDownLatch.countDown();

            }
        } else {
            try {
                countDownLatch.await();
                threadBAction();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                threadCountDownLatch.countDown();

            }
        }

    }

    public void threadAAction() {
        //耗时操作
        a = 100;
        flag = true;
    }

    public void threadBAction() {
        if (flag) {
            if (a == 0) {
                log.info("发生指令重排");
            }
        }
    }

    private HashMap cost() {
        HashMap<Object, Object> map = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            map.put("123", "123");
        }
        return map;
    }


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            CountDownLatch threadCountDownLatch = new CountDownLatch(2);
            new Thread(new VolatileOrderReset(Exec.THREAD_A.getThreadId(), countDownLatch, threadCountDownLatch)).start();
            new Thread(new VolatileOrderReset(Exec.THREAD_B.getThreadId(), countDownLatch, threadCountDownLatch)).start();
            countDownLatch.countDown();
            threadCountDownLatch.await();

        }

    }

    enum Exec {
        THREAD_A(1),
        THREAD_B(2);
        private Integer threadId;

        Exec(Integer threadId) {
            this.threadId = threadId;
        }

        public Integer getThreadId() {
            return threadId;
        }

    }


}
