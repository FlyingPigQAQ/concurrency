package com.pigcanfly.concurrency.demo;

import com.pigcanfly.concurrency.exceptions.NullException;

public class TestException {

    public void test(String str,Runnable task){
        if(str==null){
            throw new NullException();
        }


    }
}
