package com.addressbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    //private static final String DB_URL = "jdbc:mysql://localhost:3306/contacts";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/contacts?useSSL=false&serverTimezone=UTC";

    public static Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, "root", "");
    }

    public static void initializeDatabase() throws ClassNotFoundException{
        try (Connection connection = connect()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS contacts (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "email VARCHAR(255) NOT NULL," +
                    "phone VARCHAR(15) NOT NULL" +
                    ")";
            connection.createStatement().execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }
}
