package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/PokerAnalysisDB";
    private static final String USER = "root";  // Ersetze durch deinen MySQL-Benutzernamen
    //private static final String PASSWORD = System.getenv("MYSQL_PASSWORD");
    private static final String PASSWORD = "Po9Iu7Zt5";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}