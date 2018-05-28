package com.pigcanfly.concurrency.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class FutureExample  {


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(new MyCallAble());
        log.info("Main is doing something else");
        Thread.sleep(1000);
        //get()方法会在结果没计算完成时，阻塞
        log.info("Result:{}",future.get());
        service.shutdown();
    }

    static  class MyCallAble implements Callable<String>{
        @Override
        public String call() throws Exception {
            log.info("Compute in running");
            Thread.sleep(5000);
            return "Done";
        }
    }
}
