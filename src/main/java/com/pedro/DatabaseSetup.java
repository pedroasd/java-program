package com.pedro;

import java.sql.*;

public class DatabaseSetup {

    private final Connection connection;

    public DatabaseSetup(Connection connection) {
        this.connection = connection;
    }

    public void initialize() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create the files table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS files (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "file_data LONGBLOB NOT NULL," +
                    "upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "expiration_date TIMESTAMP NULL" +
                    ")";
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'files' created.");

            // Create the index on table
            String query = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_NAME = ? AND INDEX_NAME = ?";
            try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
                prepareStatement.setString(1, "files");
                prepareStatement.setString(2, "idx_name");
                ResultSet rs = prepareStatement.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    String createNameIndexSQL = "CREATE INDEX idx_name ON files (name)";
                    stmt.executeUpdate(createNameIndexSQL);
                    System.out.println("Index 'idx_name' created.");
                } else {
                    System.out.println("Index already exists.");
                }
            }

            // Create stored procedure to save a file
            String saveFileProcedure = "CREATE PROCEDURE IF NOT EXISTS SaveFile(IN fileName VARCHAR(255), IN fileData LONGBLOB, IN expireDate TIMESTAMP) " +
                    "BEGIN " +
                    "INSERT INTO files (name, file_data, upload_date, expiration_date) " +
                    "VALUES (fileName, fileData, NOW(), expireDate); " +
                    "END;";
            stmt.executeUpdate(saveFileProcedure);
            System.out.println("Stored procedure 'SaveFile' created.");

            // Create stored procedure to retrieve a file
            String getFileProcedure = "CREATE PROCEDURE IF NOT EXISTS GetFile(IN fileName VARCHAR(255)) " +
                    "BEGIN " +
                    "SELECT name, file_data, upload_date, expiration_date " +
                    "FROM files " +
                    "WHERE name = fileName; " +
                    "END;";
            stmt.executeUpdate(getFileProcedure);
            System.out.println("Stored procedure 'GetFile' created.");
        }
    }
}
