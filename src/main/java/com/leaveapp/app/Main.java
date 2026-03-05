package com.leaveapp.app;

import com.leaveapp.db.DatabaseInitializer;
import com.leaveapp.db.Seeder;
import com.leaveapp.ui.LoginUI;

public class Main {

    public static void main(String[] args) {

        System.out.println("Starting Leave Management System...");

        DatabaseInitializer.init();

        Seeder.seedAdmin();

        LoginUI.run();
    }
}