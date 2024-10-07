package com.pedro.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StoredProcedureDao {

    public record User(Integer id, String name, String email, Date birthday) {}

    private final Connection connection;

    public StoredProcedureDao(Connection connection) {
        this.connection = connection;
    }

    // CRUD for Users
    public void addUser(String name, String surname, Date birthdate) throws SQLException {
        String sql = "INSERT INTO Users (name, surname, birthdate) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setDate(3, birthdate);
            stmt.executeUpdate();
        }
    }

    public User getUser(int id) throws SQLException {
        String sql = "SELECT * FROM Users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getDate("birthdate"));
            }
        }
        return null;
    }

    public void updateUser(int id, String name, String surname, Date birthdate) throws SQLException {
        String sql = "UPDATE Users SET name = ?, surname = ?, birthdate = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setDate(3, birthdate);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM Users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // CRUD for Posts
    public void addPost(int userId, String text, Timestamp timestamp) throws SQLException {
        String sql = "INSERT INTO Posts (userId, text, timestamp) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, text);
            stmt.setTimestamp(3, timestamp);
            stmt.executeUpdate();
        }
    }

    // Complex SQL for Reports
    public ResultSet getUserPostCountReport() throws SQLException {
        String sql = "SELECT u.id, u.name, COUNT(p.id) AS post_count " +
                "FROM Users u LEFT JOIN Posts p ON u.id = p.userId " +
                "GROUP BY u.id";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    public ResultSet getPostsWithLikes() throws SQLException {
        String sql = "SELECT p.id, p.text, COUNT(l.userid) AS like_count " +
                "FROM Posts p LEFT JOIN Likes l ON p.id = l.postid " +
                "GROUP BY p.id";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    public List<String> getStoredProcedures() throws SQLException {
        String sql = "SHOW PROCEDURE STATUS where db ='test'";
        Statement stmt = connection.createStatement();
        var rs = stmt.executeQuery(sql);
        List<String> procedures = new ArrayList<>();
        while (rs.next()) {
            procedures.add(rs.getString(2));
            System.out.println("Stored Procedure: " + rs.getString(2));
        }
        return procedures;
    }

    public void dropStoredProcedures(List<String> procedures) throws SQLException {
        for (String proc : procedures) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DROP PROCEDURE IF EXISTS " + proc);
            }
        }
    }

    public void createCRUDStoredProcedures() throws SQLException {
        String[] procedures = {
                "CREATE PROCEDURE AddUser(IN p_name VARCHAR(255), IN p_surname VARCHAR(255), IN p_birthdate DATE) " +
                        "BEGIN INSERT INTO Users (name, surname, birthdate) VALUES (p_name, p_surname, p_birthdate); END;",

                "CREATE PROCEDURE GetUser(IN p_id INT) " +
                        "BEGIN SELECT * FROM Users WHERE id = p_id; END;",

                "CREATE PROCEDURE UpdateUser(IN p_id INT, IN p_name VARCHAR(255), IN p_surname VARCHAR(255), IN p_birthdate DATE) " +
                        "BEGIN UPDATE Users SET name = p_name, surname = p_surname, birthdate = p_birthdate WHERE id = p_id; END;",

                "CREATE PROCEDURE DeleteUser(IN p_id INT) " +
                        "BEGIN DELETE FROM Users WHERE id = p_id; END;",

                "CREATE PROCEDURE AddPost(IN p_userId INT, IN p_text TEXT, IN p_timestamp TIMESTAMP) " +
                        "BEGIN INSERT INTO Posts (userId, text, timestamp) VALUES (p_userId, p_text, p_timestamp); END;",
        };

        try (Statement stmt = connection.createStatement()) {
            for (String proc : procedures) {
                stmt.execute(proc);
            }
        }
    }

    public void createQueryStoredProcedures() throws SQLException {
        String[] procedures = {
                "CREATE PROCEDURE UserPostCountReport() " +
                        "BEGIN SELECT u.id, u.name, COUNT(p.id) AS post_count FROM Users u LEFT JOIN Posts p ON u.id = p.userId GROUP BY u.id; END;",

                "CREATE PROCEDURE PostsWithLikes() " +
                        "BEGIN SELECT p.id, p.text, COUNT(l.userid) AS like_count FROM Posts p LEFT JOIN Likes l ON p.id = l.postid GROUP BY p.id; END;"
        };

        try (Statement stmt = connection.createStatement()) {
            for (String proc : procedures) {
                stmt.execute(proc);
            }
        }
    }

    public void createTables() throws SQLException {
        String createUsers = "CREATE TABLE IF NOT EXISTS Users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "surname VARCHAR(255), " +
                "birthdate DATE);";

        String createPosts = "CREATE TABLE IF NOT EXISTS Posts (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "userId INT, " +
                "text TEXT, " +
                "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (userId) REFERENCES Users(id));";

        String createLikes = "CREATE TABLE IF NOT EXISTS Likes (" +
                "postid INT, " +
                "userid INT, " +
                "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (postid) REFERENCES Posts(id), " +
                "FOREIGN KEY (userid) REFERENCES Users(id), " +
                "PRIMARY KEY (postid, userid));";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createUsers);
            stmt.execute(createPosts);
            stmt.execute(createLikes);
        }
    }

    public void addUserProcedure(String name, String surname, Date birthdate) throws SQLException {
        try (CallableStatement stmt = connection.prepareCall("{call AddUser(?, ?, ?)}")) {
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setDate(3, birthdate);
            stmt.executeUpdate();
        }
    }

    public User getUserProcedure(int id) throws SQLException {
        try (CallableStatement stmt = connection.prepareCall("{call GetUser(?)}")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getDate("birthdate"));
            }
        }
        return null;
    }
}
