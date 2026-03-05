package com.leaveapp.service;

import com.leaveapp.model.Employee;
import com.leaveapp.repository.EmployeeRepository;
import com.leaveapp.util.PasswordUtil;

public class AuthService {

    private final EmployeeRepository repository = new EmployeeRepository();

    public Employee login(String username, String password) {
        String hashed = PasswordUtil.hashPassword(password);
        return repository.findByUsernameAndPassword(username, hashed);
    }

    public boolean changePassword(int employeeId, String oldPassword, String newPassword) {
        Employee employee = repository.findById(employeeId);
        if (employee == null) {
            return false;
        }

        String oldHashed = PasswordUtil.hashPassword(oldPassword);
        if (!employee.getPassword().equals(oldHashed)) {
            return false; 
        }

        String newHashed = PasswordUtil.hashPassword(newPassword);
        repository.updatePassword(employeeId, newHashed);
        
        return true; 
    }
}