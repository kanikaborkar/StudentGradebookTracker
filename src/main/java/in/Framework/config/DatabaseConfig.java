package in.Framework.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    // Port 3306 is the standard MySQL port
    private static final String URL = "jdbc:mysql://localhost:3306/gradebook_db";
    private static final String USER = "root";
    private static final String PASSWORD = "kanika_B@22"; // Set your MySQL password here

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}