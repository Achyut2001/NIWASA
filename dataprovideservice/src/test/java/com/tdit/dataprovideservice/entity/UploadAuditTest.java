package com.tdit.dataprovideservice.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UploadAuditTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        UploadAudit audit = new UploadAudit();
        UUID id = UUID.randomUUID();

        audit.setUploadId(id);
        audit.setFileName("data.xlsx");
        audit.setUploadedBy("user1");
        audit.setTimestamp(LocalDateTime.now());
        audit.setStatus(UploadAudit.UploadStatus.PROCESSING);
        audit.setTotalRows(100);
        audit.setSuccessRows(80);
        audit.setFailedRows(15);
        audit.setWarningRows(5);

        UploadAudit.RowResult rowResult = new UploadAudit.RowResult(true, null, null, 1);
        audit.setRowResults(Map.of(1, rowResult));

        assertEquals(id, audit.getUploadId());
        assertEquals("data.xlsx", audit.getFileName());
        assertEquals(100, audit.getTotalRows());
        assertEquals(UploadAudit.UploadStatus.PROCESSING, audit.getStatus());
        assertTrue(audit.getRowResults().containsKey(1));
        assertTrue(audit.getRowResults().get(1).isSuccess());
    }

    @Test
    void testAllArgsConstructor() {
        UploadAudit.RowResult rowResult = new UploadAudit.RowResult(false, "Error", "Warning", 2);
        Map<Integer, UploadAudit.RowResult> rowResults = Map.of(2, rowResult);
        LocalDateTime now = LocalDateTime.now();
        UUID id = UUID.randomUUID();

        UploadAudit audit = new UploadAudit(
                id, "file.xlsx", "user2", now,
                UploadAudit.UploadStatus.COMPLETED,
                rowResults, 50, 45, 3, 2
        );

        assertEquals(id, audit.getUploadId());
        assertEquals("file.xlsx", audit.getFileName());
        assertEquals("user2", audit.getUploadedBy());
        assertEquals(50, audit.getTotalRows());
        assertEquals(UploadAudit.UploadStatus.COMPLETED, audit.getStatus());
        assertEquals(2, audit.getRowResults().get(2).getRowNumber());
        assertEquals("Error", audit.getRowResults().get(2).getErrorMessage());
    }

    @Test
    void testBuilderPattern() {
        LocalDateTime now = LocalDateTime.now();
        UploadAudit.RowResult rowResult = UploadAudit.RowResult.builder()
                .success(true)
                .rowNumber(1)
                .errorMessage(null)
                .warningMessage(null)
                .build();

        UploadAudit audit = UploadAudit.builder()
                .uploadId(UUID.randomUUID())
                .fileName("test.xlsx")
                .uploadedBy("tester")
                .timestamp(now)
                .status(UploadAudit.UploadStatus.FAILED)
                .rowResults(Map.of(1, rowResult))
                .totalRows(10)
                .successRows(7)
                .failedRows(2)
                .warningRows(1)
                .build();

        assertEquals("test.xlsx", audit.getFileName());
        assertEquals(UploadAudit.UploadStatus.FAILED, audit.getStatus());
        assertTrue(audit.getRowResults().get(1).isSuccess());
    }

    @Test
    void testNestedClassEqualsHashCodeToString() {
        UploadAudit.RowResult r1 = new UploadAudit.RowResult(true, "Err", "Warn", 1);
        UploadAudit.RowResult r2 = new UploadAudit.RowResult(true, "Err", "Warn", 1);

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertTrue(r1.toString().contains("Err"));
    }

    @Test
    void testUploadStatusEnum() {
        assertEquals(UploadAudit.UploadStatus.PROCESSING, UploadAudit.UploadStatus.valueOf("PROCESSING"));
        assertEquals(UploadAudit.UploadStatus.COMPLETED, UploadAudit.UploadStatus.valueOf("COMPLETED"));
        assertEquals(UploadAudit.UploadStatus.FAILED, UploadAudit.UploadStatus.valueOf("FAILED"));
    }
}
