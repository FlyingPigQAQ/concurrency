package com.pigcanfly.concurrency.gc;

import java.util.ArrayList;

public class PrintGCExample {

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 20; i++) {
            ArrayList<String> arrays = new ArrayList<>();
            for (int j= 0; j < 1000000; j++) {
                arrays.add("123");
            }
            //arrays=null;

        }

    }
}
