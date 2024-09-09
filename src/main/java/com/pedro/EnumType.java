package com.pedro;

public enum EnumType {
    VARCHAR("VARCHAR(50)"),
    BOOLEAN( "BOOLEAN"),
    INTEGER("INT"),
    BIGINT("BIGINT"),
    DATE("DATE")
    ;

    private String name;

    EnumType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
