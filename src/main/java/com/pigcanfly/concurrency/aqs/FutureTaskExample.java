package com.pigcanfly.concurrency.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class FutureTaskExample {


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final ExecutorService service = Executors.newCachedThreadPool();
        final FutureTask<String> future = new FutureTask<String>(()->{
            log.info("Compute in running");
            Thread.sleep(2000);
            return "Done";
        });
        service.submit(future);
        log.info("Main is doing something else");
        Thread.sleep(1000);
        //get()方法会在结果没计算完成时，阻塞
        log.info("Result:{}",future.get());
        service.shutdown();
    }


}
