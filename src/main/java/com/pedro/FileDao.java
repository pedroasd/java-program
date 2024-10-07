package com.pedro;

import java.io.InputStream;
import java.sql.*;

public interface FileDao {
    void saveFile(String fileName, InputStream fileData, Timestamp expirationDate) throws SQLException;
    FileDaoImpl.File getFile(String fileName) throws SQLException;
    void deleteFile(int id) throws UnsupportedOperationException;
}


