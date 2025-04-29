package com.casino.future;

public class FutureExample {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        MenuService menuService = new MenuService(taskManager);
        menuService.start();
    }
}