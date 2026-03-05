package com.leaveapp.ui;

import com.leaveapp.model.Employee;
import com.leaveapp.service.AuthService;
import com.leaveapp.util.AppScannerUtil;

import java.util.Scanner;

public class LoginUI {

    public static void run() {

        Scanner sc = AppScannerUtil.get();
        AuthService authService = new AuthService();
        while(true) {
            System.out.println("\n=== Login ===");
            System.out.print("Username: ");
            String username = sc.nextLine().trim();

            System.out.print("Password: ");
            String password = sc.nextLine().trim();

        Employee employee = authService.login(username, password);

        if (employee != null) {
            if (employee.getRole().equalsIgnoreCase("Manager")) {
                ManagerMenu.run(employee);
            } else {
                EmployeeMenu.run(employee);
            }
        }
        else{
            System.out.println("❌ Invalid username/password.");
        }
        }
    }
}