package com.pigcanfly.concurrency.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class LockExample1 {

    private final static Lock lock = new ReentrantLock();
    private final static Integer TOTAL = 5000;
    private static Integer count = 0;


    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(TOTAL);
        final ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(20);
        for (int i = 0; i < TOTAL; i++) {
            service.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
        countDownLatch.await();
        log.info("count:{}", count);
        service.shutdown();
    }

    private static void add() {
        lock.lock();
        count++;
        lock.unlock();
    }

}
