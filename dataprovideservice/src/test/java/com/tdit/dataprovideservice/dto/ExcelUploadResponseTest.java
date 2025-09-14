package com.tdit.dataprovideservice.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ExcelUploadResponseTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        ExcelUploadResponse response = new ExcelUploadResponse();
        UUID id = UUID.randomUUID();

        response.setUploadId(id);
        response.setFileName("data.xlsx");
        response.setTotalRows(100);
        response.setSuccessRows(80);
        response.setFailedRows(15);
        response.setWarningRows(5);
        response.setStatus("COMPLETED");
        response.setMessage("Upload successful");

        assertEquals(id, response.getUploadId());
        assertEquals("data.xlsx", response.getFileName());
        assertEquals(100, response.getTotalRows());
        assertEquals(80, response.getSuccessRows());
        assertEquals(15, response.getFailedRows());
        assertEquals(5, response.getWarningRows());
        assertEquals("COMPLETED", response.getStatus());
        assertEquals("Upload successful", response.getMessage());
    }

    @Test
    void testBuilderPattern() {
        UUID id = UUID.randomUUID();

        ExcelUploadResponse response = ExcelUploadResponse.builder()
                .uploadId(id)
                .fileName("report.xlsx")
                .totalRows(50)
                .successRows(45)
                .failedRows(5)
                .warningRows(0)
                .status("PROCESSING")
                .message("Upload in progress")
                .build();

        assertEquals(id, response.getUploadId());
        assertEquals("report.xlsx", response.getFileName());
        assertEquals(50, response.getTotalRows());
        assertEquals(45, response.getSuccessRows());
        assertEquals(5, response.getFailedRows());
        assertEquals(0, response.getWarningRows());
        assertEquals("PROCESSING", response.getStatus());
        assertEquals("Upload in progress", response.getMessage());
    }
}
