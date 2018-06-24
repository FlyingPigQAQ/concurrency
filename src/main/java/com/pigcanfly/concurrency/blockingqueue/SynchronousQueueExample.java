package com.pigcanfly.concurrency.blockingqueue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
@Slf4j
public  class SynchronousQueueExample {

    private final Integer CPUS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        final SynchronousQueue<String> queue = new SynchronousQueue<>();
        final ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(()->{
            try {
                for (int i = 0; i < 10; i++) {

                    queue.put("123");
                    log.info("第{}次插入数据",i+1);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.execute(()->{

            try {
                 String result = queue.take();
                 log.info("Result:{}",result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.shutdown();
    }

}
