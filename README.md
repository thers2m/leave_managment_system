Leave Management System
A console-based Java application for managing employee leave requests.
Supports two roles: Manager and Employee, with SQLite as the database.

Requirements

Java 17+
Maven 3.6+
SQLite (bundled via sqlite-jdbc dependency)


Getting Started
1. Clone the project
bashgit clone https://github.com/thers2m/leave_managment_system
cd leave_managment_system
2. Build
bashmvn compile
3. Run
bashmvn exec:java

Login as Manager

Username: admin
Password: admin123

Manager can:

Create new employee accounts (EMPLOYEE or MANAGER role)
View all employees
View all leave requests
Approve or reject leave requests


Login as Employee
Employee can:

Change password
Submit a leave request
View own leave request history with status
