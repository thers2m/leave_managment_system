package com.leaveapp.ui;

import com.leaveapp.model.Employee;
import com.leaveapp.model.LeaveRequest;
import com.leaveapp.repository.EmployeeRepository;
import com.leaveapp.service.EmployeeService;
import com.leaveapp.service.LeaveService;
import com.leaveapp.util.AppScannerUtil;

import java.util.List;
import java.util.Scanner;

public class ManagerMenu {

    public static void run(Employee manager) {

        Scanner scanner = AppScannerUtil.get();

        EmployeeService employeeService = new EmployeeService();
        EmployeeRepository employeeRepository = new EmployeeRepository();
        LeaveService leaveService = new LeaveService();

        while (true) {

            System.out.println("\n==== Manager Menu ====");
            System.out.println("1. Create Employee");
            System.out.println("2. View Employees");
            System.out.println("3. View Leave Requests");
            System.out.println("4. Logout");

            int choice = AppScannerUtil.readInt("Choose: ");

            if (choice == 1) {

                // ✅ username khai báo ngoài while để dùng được bên dưới
                String username;
                while (true) {
                    System.out.print("Username: ");
                    username = scanner.nextLine().trim();
                    if (username.isEmpty()) {
                        System.out.println("Username cannot be empty.");
                        continue;
                    }
                    if (employeeRepository.findByUsername(username) != null) {
                        System.out.println("Username already exists. Please choose another.");
                        continue;
                    }
                    break;
                }

                // ✅ Validate password ngay lúc nhập
                String password;
                while (true) {
                    System.out.print("Password: ");
                    password = scanner.nextLine().trim();
                    if (!password.isEmpty()) break;
                    System.out.println("Password cannot be empty.");
                }

                // ✅ Validate full name ngay lúc nhập
                String fullName;
                while (true) {
                    System.out.print("Full name: ");
                    fullName = scanner.nextLine().trim();
                    if (!fullName.isEmpty()) break;
                    System.out.println("Full name cannot be empty.");
                }

                // ✅ Validate role
                String role;
                while (true) {
                    System.out.print("Role (EMPLOYEE/MANAGER): ");
                    role = scanner.nextLine().trim().toUpperCase();
                    if (role.equals("EMPLOYEE") || role.equals("MANAGER")) break;
                    System.out.println("Invalid role. Please enter EMPLOYEE or MANAGER.");
                }

                employeeService.createEmployee(username, password, fullName, role);
                System.out.println("Employee created!");

            } else if (choice == 2) {

                List<Employee> employees = employeeRepository.findAll();

                if (employees.isEmpty()) {
                    System.out.println("No employees found.");
                    continue;
                }

                System.out.println("\nID | Username | Full Name | Role");
                System.out.println("----------------------------------");
                for (Employee e : employees) {
                    System.out.println(
                            e.getId()       + " | " +
                            e.getUsername() + " | " +
                            e.getFullName() + " | " +
                            e.getRole()
                    );
                }

            } else if (choice == 3) {

                List<LeaveRequest> requests = leaveService.getAllRequests();

                if (requests.isEmpty()) {
                    System.out.println("No leave requests found.");
                    continue;
                }

                System.out.println("\n=== Leave Requests ===");
                System.out.println("RequestID | EmployeeID | Start -> End | Status");
                System.out.println("------------------------------------------------");
                for (LeaveRequest r : requests) {
                    System.out.println(
                            r.getId()         + " | " +
                            r.getEmployeeId() + " | " +
                            r.getStartDate()  + " -> " +
                            r.getEndDate()    + " | " +
                            r.getStatus()
                    );
                }

                System.out.println("\n1. Approve");
                System.out.println("2. Reject");
                System.out.println("3. Back");

                int action = AppScannerUtil.readInt("Action: ");

                if (action == 1) {
                    int id = AppScannerUtil.readInt("Request ID: ");
                    leaveService.approveRequest(id);
                    System.out.println("Approved.");

                } else if (action == 2) {
                    int id = AppScannerUtil.readInt("Request ID: ");
                    leaveService.rejectRequest(id);
                    System.out.println("Rejected.");

                } else if (action == 3) {
                    // Back

                } else {
                    System.out.println("Invalid action.");
                }

            } else if (choice == 4) {
                System.out.println("Logging out...");
                break;

            } else {
                System.out.println("Invalid choice. Please choose 1-4.");
            }
        }
    }
}