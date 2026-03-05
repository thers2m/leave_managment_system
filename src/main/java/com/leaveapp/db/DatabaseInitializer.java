package com.leaveapp.db;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void init() {

        String createEmployees = """
        CREATE TABLE IF NOT EXISTS employees (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT UNIQUE,
            password TEXT,
            full_name TEXT,
            role TEXT,
            is_active INTEGER,
            annual_leave INTEGER DEFAULT 12
        );
        """;

        String createLeave = """
        CREATE TABLE IF NOT EXISTS leave_requests (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            employee_id INTEGER,
            start_date TEXT,
            end_date TEXT,
            reason TEXT,
            status TEXT DEFAULT 'PENDING',
            created_at TEXT DEFAULT CURRENT_TIMESTAMP
        );
        """;

        try(Connection conn = Database.getConnection();
            Statement st = conn.createStatement()) {

            st.execute(createEmployees);
            st.execute(createLeave);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}