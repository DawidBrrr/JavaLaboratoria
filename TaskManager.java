
package com.casino.future;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class TaskManager {
    private final Map<Integer, Future<Integer>> futureMap = new HashMap<>();
    private int taskCounter = 0;

    public void createInitialTasks() {
        for (int i = 0; i < 10; i++) {
            createNewTask();
        }
    }

    public void createNewTask() {
        CustomFutureTask task = new CustomFutureTask(new Counter(nextInt()), taskCounter);
        futureMap.put(taskCounter++, task);
        new Thread(task).start();
    }

    public void cancelAllTasks() {
        for (Future<Integer> future : futureMap.values()) {
            future.cancel(true);
        }
    }

    public boolean cancelSingleTask(int taskId) {
        Future<Integer> future = futureMap.get(taskId);
        if (future != null && !future.isCancelled()) {
            return future.cancel(true);
        }
        return false;
    }

    public Future<Integer> getTask(int taskId) {
        return futureMap.get(taskId);
    }

    public Map<Integer, Future<Integer>> getAllTasks() {
        return futureMap;
    }

    private Integer nextInt() {
        return ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE / 10, Integer.MAX_VALUE);
    }
}