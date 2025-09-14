package com.tdit.dataprovideservice.exception;

public class InvalidNumberFormatException extends RuntimeException {
    private final String fieldName;
    private final String invalidValue;

    public InvalidNumberFormatException(String fieldName, String invalidValue, Throwable cause) {
        super("Invalid number format for field '" + fieldName + "': " + invalidValue, cause);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getInvalidValue() {
        return invalidValue;
    }
}
