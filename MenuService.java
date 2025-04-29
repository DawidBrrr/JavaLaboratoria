
package com.casino.future;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Future;

public class MenuService {
    private final TaskManager taskManager;

    public MenuService(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void start() {
        taskManager.createInitialTasks();
        showMenu();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                int choice = readInt(scanner);
                switch (choice) {
                    case 1:
                        showAllStatuses();
                        break;
                    case 2:
                        checkSingleTask(scanner);
                        break;
                    case 3:
                        cancelSingleTask(scanner);
                        break;
                    case 4:
                        taskManager.cancelAllTasks();
                        System.out.println("Anulowano wszystkie zadania.");
                        break;
                    case 5:
                        taskManager.createNewTask();
                        System.out.println("Dodano nowe zadanie "+ (taskManager.getAllTasks().size() -1));
                        break;
                    case 6:
                        running = false;
                        System.out.println("Program zakończył działanie.");
                        break;
                    default:
                        System.out.println("Niepoprawny wybór!");
                }
            }
        }
    }

    private void showMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1. Status wszystkich zadań");
        System.out.println("2. Status konkretnego zadania");
        System.out.println("3. Anuluj konkretne zadanie");
        System.out.println("4. Anuluj wszystkie zadania");
        System.out.println("5. Nowe zadanie");
        System.out.println("6. Wyjdź");
        System.out.print("Twój wybór: ");
    }

    private int readInt(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.print("Nieprawidłowy format - Wpisz liczbę: ");
            }
        }
    }

    private void showAllStatuses() {
        System.out.println("\n=== Status wszystkich zadań ===");
        for (Map.Entry<Integer, Future<Integer>> entry : taskManager.getAllTasks().entrySet()) {
            int id = entry.getKey();
            Future<Integer> future = entry.getValue();

            if (future.isCancelled()) {
                System.out.println("Zadanie " + id + ": ANULOWANE");
            } else if (future.isDone()) {
                try {
                    System.out.println("Zadanie " + id + ": ZAKOŃCZONE, wynik = " + future.get());
                } catch (Exception e) {
                    System.out.println("Zadanie " + id + ": Błąd: " + e.getMessage());
                }
            } else {
                System.out.println("Zadanie " + id + ": W TRAKCIE");
            }
        }
    }

    private void checkSingleTask(Scanner scanner) {
        System.out.print("Numer zadania: ");
        int taskId = readInt(scanner);
        Future<Integer> future = taskManager.getTask(taskId);
        if (future != null) {
            if (future.isCancelled()) {
                System.out.println("Zadanie " + taskId + " zostało anulowane");
            } else if (future.isDone()) {
                try {
                    System.out.println("Zadanie " + taskId + " zakończone. Wynik: " + future.get());
                } catch (Exception e) {
                    System.out.println("Błąd podczas pobierania wyniku: " + e.getMessage());
                }
            } else {
                System.out.println("Zadanie " + taskId + " w trakcie wykonywania");
            }
        } else {
            System.out.println("Nie znaleziono zadania o numerze " + taskId + ".");
        }
    }

    private void cancelSingleTask(Scanner scanner) {
        System.out.print("Numer zadania do anulowania: ");
        int taskId = readInt(scanner);
        if (taskManager.cancelSingleTask(taskId)) {
            System.out.println("Zadanie " + taskId + " anulowane.");
        } else {
            System.out.println("Nie udało się anulować zadania " + taskId + ".");
        }
    }
}