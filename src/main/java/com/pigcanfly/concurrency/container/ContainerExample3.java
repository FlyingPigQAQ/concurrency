package com.pigcanfly.concurrency.container;

import com.pigcanfly.concurrency.annotations.NotThreadSafe;
import com.pigcanfly.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

@Slf4j
@NotThreadSafe
public class ContainerExample3 {

    private final static Integer TOTAL = 5000;
    private final static Integer PER_NUMBER = 50;
    private final static CountDownLatch countDownLatch = new CountDownLatch(TOTAL);
    private final static Semaphore semaphore = new Semaphore(PER_NUMBER);
    private static Set<Integer> count = new HashSet<>();

    public static void start() throws InterruptedException {
        ExecutorService executors = Executors.newCachedThreadPool();
        for (int i = 0; i < TOTAL; i++) {
            final int temp = i;
            executors.submit(() -> {
                try {
                    semaphore.acquire();
                    update(temp);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();

            });
        }
        countDownLatch.await();
        executors.shutdown();
        log.info("count:{}", count.size());


    }

    private static void update(int i) {
        count.add( i);
    }

    public static void main(String[] args) throws InterruptedException {
        ContainerExample3.start();
    }
}
