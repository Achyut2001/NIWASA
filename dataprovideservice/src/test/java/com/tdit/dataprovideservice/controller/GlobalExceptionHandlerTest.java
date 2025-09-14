package com.tdit.dataprovideservice.controller;

import com.tdit.dataprovideservice.constants.ErrorResponseKeys;
import com.tdit.dataprovideservice.exception.ExcelProcessingException;
import com.tdit.dataprovideservice.exception.UploadNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleExcelException() {
        String errorMsg = "Excel failed";
        ExcelProcessingException ex = new ExcelProcessingException(errorMsg);

        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleExcelException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.get(ErrorResponseKeys.STATUS));
        assertEquals("Excel Processing Error", body.get(ErrorResponseKeys.ERROR));
        assertEquals(errorMsg, body.get(ErrorResponseKeys.MESSAGE));
        assertNotNull(body.get(ErrorResponseKeys.TIMESTAMP));
    }

    @Test
    void testHandleUploadNotFoundException() {
        String errorMsg = "Upload missing";
        UploadNotFoundException ex = new UploadNotFoundException(errorMsg);

        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleUploadNotFoundException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.NOT_FOUND.value(), body.get(ErrorResponseKeys.STATUS));
        assertEquals("Upload Not Found", body.get(ErrorResponseKeys.ERROR));
        assertEquals(errorMsg, body.get(ErrorResponseKeys.MESSAGE));
        assertNotNull(body.get(ErrorResponseKeys.TIMESTAMP));
    }

    @Test
    void testHandleGenericException() {
        String errorMsg = "Something went wrong";
        Exception ex = new Exception(errorMsg);

        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleGenericException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.get(ErrorResponseKeys.STATUS));
        assertEquals("Internal Server Error", body.get(ErrorResponseKeys.ERROR));
        assertEquals(errorMsg, body.get(ErrorResponseKeys.MESSAGE));
        assertNotNull(body.get(ErrorResponseKeys.TIMESTAMP));
    }
}
