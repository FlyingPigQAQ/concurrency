package com.pigcanfly.concurrency.waitandsignal;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Example1 {

    private final static Object lock = new Object();

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            service.execute(() -> {
                if (threadNum == 5) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("{}唤醒所有进程", threadNum);
                    synchronized (lock) {
                        //多个线程共占一个对象锁，则会随机唤醒一个
                        lock.notify();

                    }

                } else {
                    try {
                        log.info("{}线程等待", threadNum);
                        synchronized (lock) {
                            lock.wait();
                        }

                        log.info("{}线程继续", threadNum);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });

        }
        service.shutdown();
    }
}
