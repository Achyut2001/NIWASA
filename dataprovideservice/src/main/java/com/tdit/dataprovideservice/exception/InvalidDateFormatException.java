package com.tdit.dataprovideservice.exception;

import static com.tdit.dataprovideservice.constants.Constants.Invalid_date_format_for_field;

public class InvalidDateFormatException extends RuntimeException {

    private final String fieldName;
    private final String invalidValue;

    public InvalidDateFormatException(String fieldName, String invalidValue, Throwable cause) {
        super(Invalid_date_format_for_field + fieldName + "': " + invalidValue, cause);
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
