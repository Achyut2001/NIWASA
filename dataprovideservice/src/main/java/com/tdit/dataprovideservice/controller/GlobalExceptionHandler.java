package com.tdit.dataprovideservice.controller;

import com.tdit.dataprovideservice.constants.ErrorResponseKeys;
import com.tdit.dataprovideservice.exception.ExcelProcessingException;
import com.tdit.dataprovideservice.exception.PropertyNotFoundException;
import com.tdit.dataprovideservice.exception.UploadNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(ExcelProcessingException.class)
    public ResponseEntity<Map<String, Object>> handleExcelException(ExcelProcessingException ex) {
        log.error("Excel processing failed: {}", ex.getMessage(), ex);

        Map<String, Object> response = new HashMap<>();
        response.put(ErrorResponseKeys.TIMESTAMP, LocalDateTime.now());
        response.put(ErrorResponseKeys.STATUS, HttpStatus.BAD_REQUEST.value());
        response.put(ErrorResponseKeys.ERROR, "Excel Processing Error");
        response.put(ErrorResponseKeys.MESSAGE, ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UploadNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUploadNotFoundException(UploadNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put(ErrorResponseKeys.TIMESTAMP, LocalDateTime.now());
        response.put(ErrorResponseKeys.STATUS, HttpStatus.NOT_FOUND.value());
        response.put(ErrorResponseKeys.ERROR, "Upload Not Found");
        response.put(ErrorResponseKeys.MESSAGE, ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity<String> handlePropertyNotFoundException(PropertyNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put(ErrorResponseKeys.TIMESTAMP, LocalDateTime.now());
        response.put(ErrorResponseKeys.STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put(ErrorResponseKeys.ERROR, "Internal Server Error");
        response.put(ErrorResponseKeys.MESSAGE, ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
