package com.leaveapp.util;

import java.util.Scanner;

public class AppScannerUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static Scanner get() {
        return scanner;
    }

    // Dùng cái này thay cho scanner.nextInt() ở mọi nơi
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}