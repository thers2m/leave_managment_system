package com.leaveapp.ui;

import com.leaveapp.model.Employee;
import com.leaveapp.model.LeaveRequest;
import com.leaveapp.service.AuthService;
import com.leaveapp.service.LeaveService;
import com.leaveapp.util.AppScannerUtil;

import java.util.List;

public class EmployeeMenu {

    private static AuthService authService = new AuthService();
    private static LeaveService leaveService = new LeaveService();

    public static void run(Employee employee) {
        while (true) {
            System.out.println("\n=== Employee Menu ===");
            System.out.println("1. Change Password");
            System.out.println("2. Create Leave Request");
            System.out.println("3. View My Leave Requests");
            System.out.println("4. Logout");

            int choice = AppScannerUtil.readInt("Choose: ");
            switch (choice) {
                case 1 -> changePassword(employee); 
                case 2 -> createLeave(employee);    
                case 3 -> viewMyRequests(employee);
                case 4 -> {
                    System.out.println("Logout...");
                    return;
                }
                default -> System.out.println("Invalid option. Please choose 1-4.");
            }
        }
    }

    private static void changePassword(Employee employee) {
        System.out.println("\n=== Change Password ===");
        String oldPass = AppScannerUtil.readString("Old Password: ");
        
        String newPass;
        while (true) {
            newPass = AppScannerUtil.readString("New Password: ");
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

    private static void createLeave(Employee employee) {
        System.out.println("\n=== Create Leave Request ===");

        String startDate = AppScannerUtil.readDate("Start Date");
        String endDate   = AppScannerUtil.readDate("End Date");

        String reason = AppScannerUtil.readString("Reason: ");

        try {
            leaveService.createLeaveRequest(
                    employee.getId(),
                    employee.getFullName(),
                    startDate,
                    endDate,
                    reason
            );
            System.out.println("Leave request submitted.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewMyRequests(Employee employee) {
        List<LeaveRequest> requests = leaveService.getMyLeaveRequests(employee.getId());

        if (requests.isEmpty()) {
            System.out.println("You have no leave requests.");
            return;
        }

        System.out.println("\n=== My Leave Requests ===");
        System.out.printf("%-5s | %-25s | %-10s | %-20s | %s%n", "ID", "Start -> End", "Status", "Created At", "Reason");
        System.out.println("--------------------------------------------------------------------------------------");

        for (LeaveRequest r : requests) {
            String startToEnd = r.getStartDate() + " -> " + r.getEndDate();
            String reason = (r.getReason() == null) ? "" : r.getReason();
            
            System.out.printf("%-5d | %-25s | %-10s | %-20s | %s%n",
                    r.getId(), startToEnd, r.getStatus(), r.getCreatedAt(), reason);
        }
    }
}