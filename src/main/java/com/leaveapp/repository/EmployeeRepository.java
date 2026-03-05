package com.leaveapp.repository;

import com.leaveapp.db.Database;
import com.leaveapp.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    public Employee findByUsernameAndPassword(String username, String password) {

        String sql = """
            SELECT id, username, full_name, role, annual_leave
            FROM employees
            WHERE username = ? AND password = ? AND is_active = 1
        """;

        try (Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Employee(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("full_name"),
                            rs.getString("role"),
                            rs.getInt("annual_leave")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public Employee findByUsername(String username) {
        String sql = "SELECT id, username, full_name, role, annual_leave FROM employees WHERE username = ?";
        try (Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Employee(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("role"),
                        rs.getInt("annual_leave")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePassword(int employeeId, String hashedPassword) {

        String sql = "UPDATE employees SET password = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hashedPassword);
            ps.setInt(2, employeeId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Employee findById(int id) {

        String sql = """
            SELECT id, username, full_name, role, password
            FROM employees
            WHERE id = ?
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Employee(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("full_name"),
                            rs.getString("role"),
                            rs.getString("password")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public void createEmp(String username, String password, String fullName, String role) {

    String sql = """
        INSERT INTO employees(username, password, full_name, role, is_active)
        VALUES (?, ?, ?, ?, 1)
    """;

    try (Connection conn = Database.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, fullName);
        ps.setString(4, role);

        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }   
    }
    public List<Employee> findAll() {

        List<Employee> employees = new ArrayList<>();

        String sql = """
            SELECT id, username, full_name, role, annual_leave
            FROM employees
            WHERE is_active = 1
        """;

        try (Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("role"),
                        rs.getInt("annual_leave")  // ✅ giờ đã có trong SELECT
                );
                employees.add(emp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }
    public static int getLeaveQuota(int employeeId) {

        String sql = """
            SELECT annual_leave
            FROM employees
            WHERE id = ?
        """;

        try (Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("annual_leave");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
        public void deductLeave(int employeeId, int days) {
        String sql = "UPDATE employees SET annual_leave = annual_leave - ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, days);
            ps.setInt(2, employeeId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}