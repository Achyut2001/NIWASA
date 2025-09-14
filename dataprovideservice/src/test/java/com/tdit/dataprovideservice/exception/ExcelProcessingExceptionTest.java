package com.tdit.dataprovideservice.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class
ExcelProcessingExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Excel failed";
        ExcelProcessingException ex = new ExcelProcessingException(message);

        assertNotNull(ex);
        assertEquals(message, ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "Excel failed";
        Throwable cause = new RuntimeException("Cause");
        ExcelProcessingException ex = new ExcelProcessingException(message, cause);

        assertNotNull(ex);
        assertEquals(message, ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
