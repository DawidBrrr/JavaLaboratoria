package com.casino.future;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CustomFutureTask extends FutureTask<Integer> {
    private final int taskId;

    public CustomFutureTask(Callable<Integer> callable, int taskId) {
        super(callable);
        this.taskId = taskId;
    }

    @Override
    protected void done() {
        try {
            if (!isCancelled()) {
                System.out.println("Zadanie " + taskId + " zakończone. Wynik: " + get());
            } else {
                System.out.println("Zadanie " + taskId + " zostało anulowane.");
            }
        } catch (Exception e) {
            System.out.println("Błąd przy pobieraniu wyniku zadania " + taskId + ": " + e.getMessage());
        }
    }
}