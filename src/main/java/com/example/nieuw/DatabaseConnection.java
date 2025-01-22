package com.example.nieuw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/challengetest"; // Database 'project'
    private static final String USERNAME = "root"; // Jouw MySQL-gebruikersnaam
    private static final String PASSWORD = "Welkom1234!"; // Jouw MySQL-wachtwoord

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
