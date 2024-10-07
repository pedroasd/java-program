package com.pedro;

import java.io.InputStream;
import java.sql.*;

public class FileDao {

    public record File(String name, byte[] data){}

    private Connection connection;

    public FileDao(Connection connection) {
        this.connection = connection;
    }

    public void saveFile(String fileName, InputStream fileData, Timestamp expirationDate) throws SQLException {
        String sql = "{CALL SaveFile(?, ?, ?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, fileName);
            stmt.setBlob(2, fileData);
            stmt.setTimestamp(3, expirationDate);
            stmt.executeUpdate();
        }
    }

    public File getFile(String fileName) throws SQLException {
        String sql = "{CALL GetFile(?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, fileName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                byte[] data = rs.getBytes("file_data");
                return new File(name, data);
            }
        }
        return null;
    }

        public void deleteFile(int id) {
        throw new UnsupportedOperationException("Delete operation is not supported.");
    }
}
