package com.leaveapp.db;

import com.leaveapp.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Seeder {

    public static void seedAdmin() {

        String checkSql = "SELECT 1 FROM employees WHERE username = ?";
        String insertSql = """
            INSERT INTO employees(username, password, full_name, role, is_active, annual_leave)
            VALUES(?, ?, ?, ?, 1, ?)
            """;

        try (Connection conn = Database.getConnection()) {

            // 1) check exists
            try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
                ps.setString(1, "admin");
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Admin already exists. Skip seeding.");
                        return;
                    }
                }
            }
            String hashed = PasswordUtil.hashPassword("admin123");

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, "admin");
                ps.setString(2, hashed);
                ps.setString(3, "System Manager");
                ps.setString(4, "MANAGER");
                ps.setInt(5, 15);
                ps.executeUpdate();
            }

            System.out.println("Seeded admin: admin/admin123");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}