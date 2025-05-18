package com.addressbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    //private static final String DB_URL = "jdbc:mysql://localhost:3306/contacts";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/contacts?useSSL=false&serverTimezone=UTC";
    private static final String DB_NAME = "contacts";
    private static final String DB_URL_WITH_DB = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&serverTimezone=UTC";
    private static final String DB_URL_NO_DB = "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "";

    // Erstellt die Datenbank, falls sie nicht existiert
    public static void createDatabaseIfNotExists() throws ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(DB_URL_NO_DB, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error creating database: " + e.getMessage());
        }
    }

    public static Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, "root", "");
    }

    public static void initializeDatabase() throws ClassNotFoundException{
        createDatabaseIfNotExists();
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
