package com.leaveapp.ui;

import com.leaveapp.model.Employee;
import com.leaveapp.service.AuthService;
import com.leaveapp.util.AppScannerUtil;

public class LoginUI {

    private static final AuthService authService = new AuthService();

    public static void run() {
        while (true) {
            System.out.println("\n=== LEAVE MANAGEMENT SYSTEM ===");
            System.out.println("1. Login");
            System.out.println("0. Exit Application");
            int choice = AppScannerUtil.readInt("Choose: ");
            if (choice == 1) {
                processLogin();
            } else if (choice == 0) {
                System.out.println("Exiting system. Goodbye!");
                System.exit(0); 
            } else {
                System.out.println("Invalid option. Please choose 1 or 0.");
            }
        }
    }

    private static void processLogin() {
        System.out.println("\n--- Login ---");
        String username = AppScannerUtil.readString("Username: ");
        String password = AppScannerUtil.readString("Password: ");

        Employee employee = authService.login(username, password);

        if (employee != null) {
            System.out.println("Login successful! Welcome, " + employee.getFullName());
            
            if (employee.getRole().equalsIgnoreCase("MANAGER")) {
                ManagerMenu.run(employee);
            } else {
                EmployeeMenu.run(employee);
            }
        } else {
            System.out.println("Invalid username or password.");
        }
    }
}