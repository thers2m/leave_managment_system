package com.leaveapp.ui;

import com.leaveapp.model.Employee;
import com.leaveapp.model.LeaveRequest;
import com.leaveapp.service.AuthService;
import com.leaveapp.service.LeaveService;
import com.leaveapp.util.AppScannerUtil;

import java.util.List;
import java.util.Scanner;

public class EmployeeMenu {

    private static AuthService authService = new AuthService();
    private static LeaveService leaveService = new LeaveService();

    public static void run(Employee employee) {

        Scanner scanner = AppScannerUtil.get();

        while (true) {

            System.out.println("\n=== Employee Menu ===");
            System.out.println("1. Change Password");
            System.out.println("2. Create Leave Request");
            System.out.println("3. View My Leave Requests");
            System.out.println("4. Logout");

            int choice = AppScannerUtil.readInt("Choose: ");

            switch (choice) {
                case 1 -> changePassword(scanner, employee);
                case 2 -> createLeave(scanner, employee);
                case 3 -> viewMyRequests(employee);
                case 4 -> {
                    System.out.println("Logout...");
                    return;
                }
                default -> System.out.println("Invalid option. Please choose 1-4.");
            }
        }
    }

    private static void changePassword(Scanner scanner, Employee employee) {

        System.out.println("\n=== Change Password ===");

        // ✅ Validate old password không được rỗng
        String oldPass;
        while (true) {
            System.out.print("Old Password: ");
            oldPass = scanner.nextLine().trim();
            if (!oldPass.isEmpty()) break;
            System.out.println("Password cannot be empty.");
        }

        // ✅ Validate new password không được rỗng và không được trùng old
        String newPass;
        while (true) {
            System.out.print("New Password: ");
            newPass = scanner.nextLine().trim();
            if (newPass.isEmpty()) {
                System.out.println("Password cannot be empty.");
                continue;
            }
            if (newPass.equals(oldPass)) {
                System.out.println("New password must be different from old password.");
                continue;
            }
            break;
        }

        boolean success = authService.changePassword(employee.getId(), oldPass, newPass);

        if (success) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Old password incorrect.");
        }
    }

    private static void createLeave(Scanner scanner, Employee employee) {

        System.out.println("\n=== Create Leave Request ===");

        // ✅ Validate định dạng ngày
        String startDate = readDate(scanner, "Start Date (yyyy-MM-dd): ");
        String endDate   = readDate(scanner, "End Date (yyyy-MM-dd): ");

        // ✅ Validate reason không được rỗng
        String reason;
        while (true) {
            System.out.print("Reason: ");
            reason = scanner.nextLine().trim();
            if (!reason.isEmpty()) break;
            System.out.println("Reason cannot be empty.");
        }

        try {
            leaveService.createLeaveRequest(
                    employee.getId(),
                    startDate,
                    endDate,
                    reason
            );
            System.out.println("Leave request submitted.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ✅ Helper: nhập lại cho đến khi đúng định dạng yyyy-MM-dd
    private static String readDate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return input;
            }
            System.out.println("Invalid date format. Please use yyyy-MM-dd (e.g. 2026-03-15).");
        }
    }

    private static void viewMyRequests(Employee employee) {

        List<LeaveRequest> requests = leaveService.getMyLeaveRequests(employee.getId());

        if (requests.isEmpty()) {
            System.out.println("You have no leave requests.");
            return;
        }

        System.out.println("\n=== My Leave Requests ===");
        System.out.println("ID | Start -> End | Status | Created At | Reason");
        System.out.println("--------------------------------------------------");

        for (LeaveRequest r : requests) {
            System.out.println(
                r.getId()        + " | " +
                r.getStartDate() + " -> " +
                r.getEndDate()   + " | " +
                r.getStatus()    + " | " +
                r.getCreatedAt() + " | " +
                (r.getReason() == null ? "" : r.getReason())
            );
        }
    }
}