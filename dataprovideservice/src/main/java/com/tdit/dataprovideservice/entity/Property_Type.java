package com.tdit.dataprovideservice.entity;

import java.util.Arrays;

public enum Property_Type {
    APARTMENT,
    VILLA,
    PG,
    HOTEL,
    HOSTEL;


    public static Property_Type fromString(String value) {
        if (value == null) {
            return null;
        }

        String cleanValue = value.trim().replaceAll("\\s+", "");
        return Arrays.stream(Property_Type.values())
                .filter(e -> e.name().equalsIgnoreCase(cleanValue))
                .findFirst()
                .orElse(null);
    }
}
