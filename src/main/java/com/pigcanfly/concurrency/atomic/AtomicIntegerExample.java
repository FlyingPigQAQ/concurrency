package com.pigcanfly.concurrency.atomic;

import com.pigcanfly.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ThreadSafe
public class AtomicIntegerExample {

    private final static Integer TOTAL = 5000;
    private final static Integer PER_NUMBER= 50;
    private final static CountDownLatch countDownLatch = new CountDownLatch(TOTAL);
    private final static Semaphore semaphore = new Semaphore(PER_NUMBER);
    private static AtomicInteger count= new AtomicInteger(0);
    public static void start() throws InterruptedException {
        ExecutorService executors = Executors.newCachedThreadPool();
        for (int i = 0; i < TOTAL; i++) {
            executors.submit(()->{
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();

            });
        }
        countDownLatch.await();
        executors.shutdown();
        log.info("count:{}",count.get());


    }
    private static void add(){
        count.getAndIncrement();
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerExample.start();
    }
}
