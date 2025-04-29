package com.casino.future;

import java.util.concurrent.Callable;

public class Counter implements Callable<Integer> {
    private final Integer value;
    public Counter(Integer value) {
        this.value = value;
    }

    private Integer calc() {
        int returnVal = 0;
        for (int i = 0; i <= value; i++) {
            if(Thread.currentThread().isInterrupted()){
                return -1;
            }
            for (int j = 0; j <= 60; j++) {}
            returnVal = i;
        }
        return returnVal;
    }

    @Override
    public Integer call() {
        return calc();
    }
}
