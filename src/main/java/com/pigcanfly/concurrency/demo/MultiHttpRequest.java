package com.pigcanfly.concurrency.demo;

import com.pigcanfly.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author:TobbyQuinn
 * @date:2018/4/28
 * @project:concurrency
 **/
@Slf4j
@NotThreadSafe
public class MultiHttpRequest {

    public static final int totalNumbers = 5000;
    public static final int perNumbers = 50;
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(perNumbers);
        final CountDownLatch countDownLatch = new CountDownLatch(totalNumbers);
        for (int i = 0; i < totalNumbers; i++) {
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    count++;
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:"+count);

    }

}
