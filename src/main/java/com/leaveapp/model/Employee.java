package com.leaveapp.model;

public class Employee {
    private int id;
    private String username;
    private String fullName;
    private String role;
    private String password;
    private int annualLeave;

    // Constructor dùng cho login
    public Employee() {}

    // Constructor dùng cho change password
    public Employee(int id, String username, String fullName, String role, String password, int annualLeave) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.role = role;
        this.password = password;
        this.annualLeave = annualLeave;
    }

    public int getId() { return id; }

    public String getUsername() { return username; }

    public String getFullName() { return fullName; }

    public String getRole() { return role; }

    public String getPassword() { return password; }

    public int getAnnualLeave() { return annualLeave; }
}