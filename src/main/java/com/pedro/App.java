package com.pedro;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/db";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public static void main( String[] args ) {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            var databaseSetup = new DatabaseSetup(connection);
            databaseSetup.initialize();

            var fileDAO = new FileDao(connection);

            // Save a file
            String filePath = "/home/pedro-salazar/Downloads/icaclient_24.5.0.76_amd64.deb";
            var fileName = "largefile.txt";
            try (InputStream fileInputStream = new FileInputStream(filePath)) {
                fileDAO.saveFile(fileName, fileInputStream, null);
                System.out.println("File saved.");
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }

            // Get file
            var file = fileDAO.getFile(fileName);
            if (file != null) {
                System.out.println("Stored file: " + file.name());
            } else {
                System.out.println("File not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
