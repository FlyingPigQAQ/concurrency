package com.pigcanfly.concurrency.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Slf4j
public class CyclicBarrierExample {
    private final static Integer PARTIES = 5;
    private final static Integer TOTAL = 10;

    private final static CyclicBarrier barrier = new CyclicBarrier(PARTIES);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();

        for (int i = 0; i <TOTAL; i++) {
            Thread.sleep(1000);
            final int threadNum = i;
            service.execute(()->{
                doSomething(threadNum);

            });
        }
        service.shutdown();
    }

    private static void  doSomething(int threadNum){
        log.info("{} is ready",threadNum);
        try {

            barrier.await();
            log.info("{} is running",threadNum);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
