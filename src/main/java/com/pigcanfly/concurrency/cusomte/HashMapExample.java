package com.pigcanfly.concurrency.cusomte;

import java.util.HashMap;
import java.util.Map;

public class HashMapExample {

    public static void main(String[] args) {
        Map<Integer,Integer> map = new HashMap<>(2,1);
        map.put(1,1);
        map.put(3,3);
        map.put(5,5);


    }
}
