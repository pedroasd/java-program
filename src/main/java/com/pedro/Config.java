package com.pedro;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Config {

    public record TableConfig(int columns, int types, int rows){};

    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private int concurrentConnections;
    private List<TableConfig> tablesConfig;

    public Config(String propertiesAbsolutePath) throws Exception {
        Properties properties = new Properties();

        try (FileInputStream inputStream = new FileInputStream(propertiesAbsolutePath)) {
            properties.load(inputStream);

            dbUrl = getProperty(properties, "db.url");
            dbUsername = getProperty(properties, "db.username");
            dbPassword = getProperty(properties, "db.password");
            concurrentConnections = Integer.parseInt(getProperty(properties, "concurrent.connections"));

            var tables = Integer.parseInt(getProperty(properties, "random.tables"));
            var tableSetConfig = getProperty(properties, "tables.config");
            tablesConfig = new ArrayList<>();

            var configByTable = tableSetConfig.split(";");
            if (configByTable.length != tables)
                throw new Exception("Expected tables are " + tables + " from random.tables, but it mismatch to tables.config. It is " + configByTable.length);

            Arrays.stream(configByTable).forEach(config -> {
                var tableConfig = config.split(",");
                tablesConfig.add(new TableConfig(Integer.parseInt(tableConfig[0]),Integer.parseInt(tableConfig[1]),Integer.parseInt(tableConfig[2])));
            });

        } catch (Exception e) {
            throw new Exception("Failed to load properties file: " + e.getMessage(), e);
        }
    }

    private String getProperty(Properties properties, String propertyName) throws Exception {
        var value = properties.getProperty(propertyName);
        if( value == null) throw new Exception("Property " + propertyName + " is not defined.");
        return value;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public int getConcurrentConnections() {
        return concurrentConnections;
    }

    public List<TableConfig> getTablesConfig() {
        return tablesConfig;
    }
}
