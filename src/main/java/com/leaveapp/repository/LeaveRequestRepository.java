package com.leaveapp.repository;

import com.leaveapp.db.Database;
import com.leaveapp.model.LeaveRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequestRepository {

    public void createRequest(int employeeId, String employeeName, String startDate, String endDate, String reason) {

        String sql = """
            INSERT INTO leave_requests(employee_id, employee_name, start_date, end_date, reason)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setString(2, employeeName);
            ps.setString(3, startDate);
            ps.setString(4, endDate);
            ps.setString(5, reason);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<LeaveRequest> findByEmployeeId(int employeeId) {

        List<LeaveRequest> list = new ArrayList<>();

        String sql = """
            SELECT id, employee_id, employee_name, start_date, end_date, reason, status, created_at
            FROM leave_requests
            WHERE employee_id = ?
            ORDER BY id DESC
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new LeaveRequest(
                            rs.getInt("id"),
                            rs.getInt("employee_id"),
                            rs.getString("employee_name"),
                            rs.getString("start_date"),
                            rs.getString("end_date"),
                            rs.getString("reason"),
                            rs.getString("status"),
                            rs.getString("created_at")
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<LeaveRequest> findAll() {

        List<LeaveRequest> list = new ArrayList<>();

        String sql = """
            SELECT id, employee_id, employee_name, start_date, end_date, reason, status, created_at
            FROM leave_requests
            ORDER BY created_at DESC
        """;

        try (Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                list.add(new LeaveRequest(
                        rs.getInt("id"),
                        rs.getInt("employee_id"),
                        rs.getString("employee_name"),
                        rs.getString("start_date"),
                        rs.getString("end_date"),
                        rs.getString("reason"),
                        rs.getString("status"),
                        rs.getString("created_at")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateStatus(int requestId, String status) {

        String sql = """
            UPDATE leave_requests
            SET status = ?
            WHERE id = ?
        """;

        try (Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, requestId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean hasOverlap(int employeeId, String startDate, String endDate) {

        String sql = """
            SELECT COUNT(*)
            FROM leave_requests
            WHERE employee_id = ?
            AND status IN ('PENDING','APPROVED')
            AND NOT (
                end_date < ?
                OR start_date > ?
            )
        """;

        try (Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setString(2, startDate);
            ps.setString(3, endDate);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public int getUsedLeaveDays(int employeeId) {

        String sql = """
            SELECT SUM(
                JULIANDAY(end_date) - JULIANDAY(start_date) + 1
            )
            FROM leave_requests
            WHERE employee_id = ?
            AND status = 'APPROVED'
        """;

        try (Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int days = rs.getInt(1);

                if (rs.wasNull()) {
                    return 0;
                }

                return days;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

}