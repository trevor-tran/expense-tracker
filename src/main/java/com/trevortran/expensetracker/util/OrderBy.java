package com.trevortran.expensetracker.util;

public enum OrderBy {
    ASC("asc"),
    DESC("desc");

    private final String value;

    private OrderBy(String value) {
        this.value = value;
    }

    public static OrderBy stringToEnum(String value) {
        if (value.equalsIgnoreCase("desc")) {
            return OrderBy.DESC;
        }

        return OrderBy.ASC;
    }

    @Override
    public String toString() {
        return value;
    }
}
