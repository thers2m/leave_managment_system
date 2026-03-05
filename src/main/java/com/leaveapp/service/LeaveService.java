package com.leaveapp.service;

import com.leaveapp.model.LeaveRequest;
import com.leaveapp.repository.EmployeeRepository;
import com.leaveapp.repository.LeaveRequestRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class LeaveService {

    private LeaveRequestRepository repository = new LeaveRequestRepository();
    private EmployeeRepository employeeRepository = new EmployeeRepository();

    public void createLeaveRequest(int employeeId, String startDate, String endDate, String reason) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        if (start.isAfter(end)) {
            throw new RuntimeException("Invalid date range");
        }

        if (start.isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot request leave in the past");
        }

        if (repository.hasOverlap(employeeId, startDate, endDate)) {
            throw new RuntimeException("Leave request overlaps existing request");
        }

        long days = ChronoUnit.DAYS.between(start, end) + 1;

        int usedDays = repository.getUsedLeaveDays(employeeId);

        int quota = employeeRepository.getLeaveQuota(employeeId);

        if (usedDays + days > quota) {
            throw new RuntimeException("Not enough leave quota");
        }

        repository.create(new LeaveRequest(employeeId, startDate, endDate, reason));
    }

    public List<LeaveRequest> getMyLeaveRequests(int employeeId) {

        return repository.findByEmployeeId(employeeId);
    }

    public List<LeaveRequest> getAllRequests() {

        return repository.findAll();
    }

    public void approveRequest(int requestId) {
    // Lấy thông tin request để tính số ngày
    List<LeaveRequest> all = repository.findAll();
    LeaveRequest target = null;
    for (LeaveRequest r : all) {
        if (r.getId() == requestId) {
            target = r;
            break;
        }
    }

    if (target == null) {
        System.out.println("Request not found.");
        return;
    }

    // Tính số ngày nghỉ
    LocalDate start = LocalDate.parse(target.getStartDate());
    LocalDate end = LocalDate.parse(target.getEndDate());
    int days = (int) ChronoUnit.DAYS.between(start, end) + 1;

    // Cập nhật status
    repository.updateStatus(requestId, "APPROVED");

    // Trừ annual_leave của employee
    employeeRepository.deductLeave(target.getEmployeeId(), days);
}

    public void rejectRequest(int requestId) {

        repository.updateStatus(requestId, "REJECTED");
    }
}