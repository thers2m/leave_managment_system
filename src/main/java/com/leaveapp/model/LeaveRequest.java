package com.leaveapp.model;

public class LeaveRequest {

    private int id;
    private int employeeId;
    private String startDate;
    private String endDate;
    private String reason;
    private String status;
    private String createdAt;

    public LeaveRequest(int employeeId, String startDate, String endDate, String reason) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    public LeaveRequest(int id, int employeeId, String startDate, String endDate,
                        String reason, String status, String createdAt) {
        this.id = id;
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
    }
    public int getId() { return id; }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }
    public String getStatus() { return status; }
    public String getCreatedAt() { return createdAt; }
}