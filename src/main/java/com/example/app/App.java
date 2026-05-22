package com.example.app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger logger =
            LoggerFactory.getLogger(App.class);

    private static final ArrayList<String> expenseList =
            new ArrayList<>();

    private static double totalExpense = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        logger.info("Expense Tracker Started");

        int choice;

        System.out.println("======================================");
        System.out.println("         DAILY EXPENSE TRACKER");
        System.out.println("======================================");

        do {

            displayMenu();

            System.out.print("Enter your choice : ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addExpense(scanner);
                    break;

                case 2:
                    viewExpenses();
                    break;

                case 3:
                    showTotalExpense();
                    break;

                case 4:
                    System.out.println("Thank You For Using Expense Tracker");
                    logger.info("Application Closed");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 4);

        scanner.close();
    }

    public static void displayMenu() {

        System.out.println("\n========== MENU ==========");
        System.out.println("1. Add Expense");
        System.out.println("2. View Expenses");
        System.out.println("3. Show Total Expense");
        System.out.println("4. Exit");
        System.out.println("==========================");
    }

    public static void addExpense(Scanner scanner) {

        System.out.println("\nSelect Expense Category");
        System.out.println("1. Food");
        System.out.println("2. Travel");
        System.out.println("3. Shopping");
        System.out.println("4. Electricity Bill");
        System.out.println("5. Internet");
        System.out.println("6. Entertainment");
        System.out.println("7. Medical");
        System.out.println("8. Other");

        System.out.print("Enter category choice : ");

        int categoryChoice = scanner.nextInt();
        scanner.nextLine();

        String category;

        switch (categoryChoice) {

            case 1:
                category = "Food";
                break;

            case 2:
                category = "Travel";
                break;

            case 3:
                category = "Shopping";
                break;

            case 4:
                category = "Electricity Bill";
                break;

            case 5:
                category = "Internet";
                break;

            case 6:
                category = "Entertainment";
                break;

            case 7:
                category = "Medical";
                break;

            default:
                category = "Other";
        }

        System.out.print("Enter Expense Amount : ");

        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (amount <= 0) {

            logger.error("Invalid amount entered");

            System.out.println("Amount must be greater than zero.");
            return;
        }

        String expenseRecord =
                "Date: " + LocalDate.now()
                + " | Category: " + category
                + " | Amount: Rs " + amount;

        expenseList.add(expenseRecord);

        totalExpense += amount;

        logger.info("Expense Added Successfully");

        System.out.println("Expense Added Successfully!");
    }

    public static void viewExpenses() {

        System.out.println("\n======================================");
        System.out.println("            EXPENSE LIST");
        System.out.println("======================================");

        if (expenseList.isEmpty()) {

            System.out.println("No expenses recorded.");
            return;
        }

        for (String expense : expenseList) {
            System.out.println(expense);
        }

        System.out.println("======================================");
    }

    public static void showTotalExpense() {

        System.out.println("\n======================================");

        System.out.printf("Total Expense : Rs %.2f%n",
                totalExpense);

        System.out.println("======================================");
    }
}
