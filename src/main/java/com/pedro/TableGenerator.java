package com.pedro;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class TableGenerator implements Callable<Void> {

    private record Column(String name, EnumType type){};

    private String tableName;
    private int numColumns;
    private int numRows;
    private int columnTypes;
    private List<Column> columns;
    private Config config;

    public TableGenerator(String tableName, int numColumns, int numRows, int columnTypes, Config config) {
        this.tableName = tableName;
        this.numColumns = numColumns;
        this.numRows = numRows;
        this.columnTypes = columnTypes;
        this.config = config;
        columns = new ArrayList<>();
    }

    @Override
    public Void call() throws Exception {
        try (Connection conn = DriverManager.getConnection(config.getDbUrl(), config.getDbUsername(), config.getDbPassword())) {
            createTable(conn);
            populateTable(conn);
        } catch (SQLException e) {
            throw new Exception("Error creating table " + tableName + " : " + e.getMessage());
        }
        return null;
    }

    private void createTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        var random = new Random();
        var randomTypes = EnumType.values().length < columnTypes ? EnumType.values().length - 1 : columnTypes;
        StringBuilder sql = new StringBuilder("CREATE TABLE ").append(tableName).append(" (id INT PRIMARY KEY");
        columns.add(new Column("id", EnumType.INTEGER));

        for (int i = 1; i < numColumns; i++) {
            var columnName = randomString() + i;
            var columType = EnumType.values()[random.nextInt(randomTypes)];
            columns.add(new Column(columnName, columType));
            sql.append(", ").append(columnName).append(" ").append(columType.getName());
        }
        sql.append(")");

        stmt.execute(sql.toString());
        System.out.println("Table " + tableName + " created");
    }

    private void populateTable(Connection conn) throws SQLException {
        StringBuilder insertSQL = new StringBuilder("INSERT INTO ").append(tableName).append(" VALUES (");
        insertSQL.append("?,".repeat(Math.max(0, numColumns)));
        insertSQL.deleteCharAt(insertSQL.length() - 1); // Remove last comma
        insertSQL.append(")");

        try (PreparedStatement statement = conn.prepareStatement(insertSQL.toString())) {
            for (int i = 0; i < numRows; i++) { // m rows
                for (int j = 1; j <= numColumns; j++) {
                    setRandomValue(statement, j, columns.get(j-1).type);
                }
                statement.executeUpdate();
            }
        }
        System.out.println("Table " + tableName + " populated with " + numRows + " records.");
    }

    private static void setRandomValue(PreparedStatement statement, int position, EnumType type) throws SQLException {
        switch (type){
            case VARCHAR -> statement.setString(position, randomString());
            case BOOLEAN -> statement.setBoolean(position, new Random().nextBoolean());
            case INTEGER -> statement.setInt(position, new Random().nextInt());
            case BIGINT -> statement.setLong(position, new Random().nextLong());
            case DATE -> statement.setDate(position, Date.valueOf(LocalDate.now()));
        }
    }

    public static String randomString() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }
        return sb.toString();
    }
}

