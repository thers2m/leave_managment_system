package com.leaveapp.ui;

import com.leaveapp.model.Employee;
import com.leaveapp.model.LeaveRequest;
import com.leaveapp.repository.EmployeeRepository;
import com.leaveapp.service.EmployeeService;
import com.leaveapp.service.LeaveService;
import com.leaveapp.util.AppScannerUtil;

import java.util.List;

public class ManagerMenu {

    private static final EmployeeService employeeService = new EmployeeService();
    private static final EmployeeRepository employeeRepository = new EmployeeRepository();
    private static final LeaveService leaveService = new LeaveService();

    public static void run(Employee manager) {
        while (true) {
            System.out.println("\n==== Manager Menu ====");
            System.out.println("1. Create Employee");
            System.out.println("2. View Employees");
            System.out.println("3. View Leave Requests");
            System.out.println("4. Logout");

            int choice = AppScannerUtil.readInt("Choose: ");

            switch (choice) {
                case 1 -> createEmployee();
                case 2 -> viewEmployees();
                case 3 -> manageLeaveRequests();
                case 4 -> {
                    System.out.println("Logging out...");
                    return; 
                }
                default -> System.out.println("Invalid choice. Please choose 1-4.");
            }
        }
    }

    private static void createEmployee() {
        System.out.println("\n=== Create Employee ===");
        String username;
        while(true){
            username = AppScannerUtil.readString("Username: ");
            if(employeeService.checkExist(username)) {
            System.out.println("Username already exists. Please choose another.");
            }else {
                break;
            }
        }


        String password = AppScannerUtil.readString("Password: ");
        String fullName = AppScannerUtil.readString("Full name: ");

        String role;
        while (true) {
            role = AppScannerUtil.readString("Role (EMPLOYEE/MANAGER): ").toUpperCase();
            if (role.equals("EMPLOYEE") || role.equals("MANAGER")) {
                break;
            }
            System.out.println("Invalid role. Please enter EMPLOYEE or MANAGER.");
        }

        employeeService.createEmployee(username, password, fullName, role);
        System.out.println("Employee created!");
    }

    private static void viewEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("\n=== Employee List ===");
        System.out.printf("%-5s | %-15s | %-20s | %-10s%n", "ID", "Username", "Full Name", "Role");
        System.out.println("-------------------------------------------------------------");
        
        for (Employee e : employees) {
            System.out.printf("%-5d | %-15s | %-20s | %-10s%n",
                    e.getId(), e.getUsername(), e.getFullName(), e.getRole());
        }
    }

    private static void manageLeaveRequests() {
        List<LeaveRequest> requests = leaveService.getAllRequests();

        if (requests.isEmpty()) {
            System.out.println("No leave requests found.");
            return;
        }

        System.out.println("\n=== Leave Requests ===");
        System.out.printf("%-5s | %-6s | %-20s | %-25s | %-10s%n", "ReqID", "EmpID", "Employee Name", "Start -> End", "Status");
        System.out.println("--------------------------------------------------------------------------------------");
        
        for (LeaveRequest r : requests) {
            String dates = r.getStartDate() + " -> " + r.getEndDate();
            System.out.printf("%-5d | %-6d | %-20s | %-25s | %-10s%n",
                    r.getId(), r.getEmployeeId(), r.getEmployeeName(), dates, r.getStatus());
        }

        System.out.println("\n1. Approve");
        System.out.println("2. Reject");
        System.out.println("3. Back");

        int action = AppScannerUtil.readInt("Action: ");

        if (action == 1 || action == 2) {
            int id = AppScannerUtil.readInt("Request ID: ");
            if (action == 1) {
                leaveService.approveRequest(id);
                System.out.println("Approved.");
            } else {
                leaveService.rejectRequest(id);
                System.out.println("Rejected.");
            }
        } else if (action == 3) {
        } else {
            System.out.println("Invalid action.");
        }
    }
}