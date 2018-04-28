package com.pigcanfly.concurrency.atomic;

import com.pigcanfly.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author:TobbyQuinn
 * @date:2018/4/28
 * @project:concurrency
 **/
@Slf4j
@ThreadSafe
public class AtomicExample1 {
    public static final int totalNumbers = 5000;
    public static final int perNumbers = 50;
    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(perNumbers);
        final CountDownLatch countDownLatch = new CountDownLatch(totalNumbers);
        for (int i = 0; i < totalNumbers; i++) {
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    count.getAndIncrement();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        //如果不加await()，则主线程会直接调用shutdown方法，此时已经提交的线程会执行完毕，但不接受新的任务提交
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:"+count.get());

    }
}
