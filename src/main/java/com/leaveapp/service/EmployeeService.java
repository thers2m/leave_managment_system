package com.leaveapp.service;

import com.leaveapp.db.Database;
import com.leaveapp.model.Employee;
import com.leaveapp.repository.EmployeeRepository;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeService {

    private EmployeeRepository employeeRepository = new EmployeeRepository();

    public void createEmployee(String username, String password, String fullName, String role) {

        String hashedPassword = hashPassword(password);

        employeeRepository.createEmp(username, hashedPassword, fullName, role);
    }

    private String hashPassword(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean changePassword(int employeeId, String oldPassword, String newPassword) {

    Employee emp = employeeRepository.findById(employeeId);

    String oldHash = hashPassword(oldPassword);

    if (!emp.getPassword().equals(oldHash)) {
        return false;
    }

    String newHash = hashPassword(newPassword);

        employeeRepository.updatePassword(employeeId, newHash);

        return true;
        }
}