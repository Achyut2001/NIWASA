package com.tdit.dataprovideservice.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UploadNotFoundExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Upload not found";
        UploadNotFoundException ex = new UploadNotFoundException(message);

        assertNotNull(ex);
        assertEquals(message, ex.getMessage());
        assertNull(ex.getCause());
    }
}
