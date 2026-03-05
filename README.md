Leave Management System: 
A console-based Java application for managing employee leave requests. Supports two roles: Manager and Employee, with SQLite as the database.

Requirements:
Java 17+
Maven 3.6+
SQLite (bundled via sqlite-jdbc dependency)

 1. Clone the project
git clone https://github.com/thers2m/leave_managment_system
cd leave_managment_system

2. Build
mvn compile

4. run
mvn exec:java

Login to a manager:
username: admin
password: admin123

In here, you can:
1. Create new employee accounts (EMPLOYEE or MANAGER role)
2. View all employees
3. View all leave requests
4. Approve or reject leave requests

Employee:
1. Change password
2. Submit a leave request
3. View own leave request history with status
