package com.trevortran.expensetracker.util;

public enum SortBy {
    DATE("date"),
    DESCRIPTION("description"),
    CATEGORY("category"),
    AMOUNT("amount");

    private final String value;

    private SortBy(String value) {
        this.value = value;
    }

    public static SortBy stringToEnum(String value) {
        if (value.equalsIgnoreCase("description")) {
            return SortBy.DESCRIPTION;
        } else if (value.equalsIgnoreCase("category")) {
            return SortBy.CATEGORY;
        } else if (value.equalsIgnoreCase("amount")) {
            return SortBy.AMOUNT;
        }

        return SortBy.DATE;
    }

    @Override
    public String toString() {
        return value;
    }
}
