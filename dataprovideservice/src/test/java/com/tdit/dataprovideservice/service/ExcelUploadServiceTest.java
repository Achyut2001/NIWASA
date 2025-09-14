package com.tdit.dataprovideservice.service;

import com.tdit.dataprovideservice.constants.Constants;
import com.tdit.dataprovideservice.dto.ExcelRowData;
import com.tdit.dataprovideservice.dto.ExcelUploadResponse;
import com.tdit.dataprovideservice.entity.Property;
import com.tdit.dataprovideservice.entity.UploadAudit;
import com.tdit.dataprovideservice.exception.UploadNotFoundException;
import com.tdit.dataprovideservice.repository.PropertyRepository;
import com.tdit.dataprovideservice.repository.UploadAuditRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExcelUploadServiceTest {

    @Mock
    private ExcelProcessorServiceIml excelProcessorService;
    @Mock
    private ExcelValidationServiceImp excelValidationService;
    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private UploadAuditRepository uploadAuditRepository;
    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ExcelUploadServiceImp excelUploadService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        when(multipartFile.getOriginalFilename()).thenReturn("test.xlsx");
    }

    private UploadAudit buildAudit() {
        return UploadAudit.builder()
                .uploadId(UUID.randomUUID())
                .fileName("test.xlsx")
                .uploadedBy("tester")
                .timestamp(LocalDateTime.now())
                .status(UploadAudit.UploadStatus.PROCESSING)
                .rowResults(new HashMap<>())
                .build();
    }

    @Test
    void testProcessExcelUpload_success() throws IOException {
        UploadAudit audit = buildAudit();
        when(uploadAuditRepository.save(any())).thenReturn(audit);

        ExcelRowData rowData = new ExcelRowData();
        when(excelProcessorService.processExcelFile(multipartFile)).thenReturn(List.of(rowData));

        UploadAudit.RowResult validationResult = UploadAudit.RowResult.builder()
                .success(true)
                .rowNumber(2)
                .build();
        when(excelValidationService.validateRow(eq(rowData), anyInt())).thenReturn(validationResult);

        Property property = new Property();
        when(excelProcessorService.convertToProperty(rowData)).thenReturn(property);

        ExcelUploadResponse response = excelUploadService.processExcelUpload(multipartFile, "tester");

        assertNotNull(response);
        assertEquals(1, response.getSuccessRows());
        assertEquals(Constants.STATUS_COMPLETED, response.getStatus());
        verify(propertyRepository, times(1)).saveAll(anyList());
        verify(uploadAuditRepository, atLeast(2)).save(any());
    }

    @Test
    void testProcessExcelUpload_withNullRow() throws IOException {
        UploadAudit audit = buildAudit();
        when(uploadAuditRepository.save(any())).thenReturn(audit);

        List<ExcelRowData> rows = new ArrayList<>();
        rows.add(null);
        when(excelProcessorService.processExcelFile(multipartFile)).thenReturn(rows);

        ExcelUploadResponse response = excelUploadService.processExcelUpload(multipartFile, "tester");

        assertEquals(0, response.getSuccessRows());
        assertEquals(1, response.getFailedRows());
    }

    @Test
    void testProcessExcelUpload_validationFails() throws IOException {
        UploadAudit audit = buildAudit();
        when(uploadAuditRepository.save(any())).thenReturn(audit);

        ExcelRowData rowData = new ExcelRowData();
        when(excelProcessorService.processExcelFile(multipartFile)).thenReturn(List.of(rowData));

        UploadAudit.RowResult validationResult = UploadAudit.RowResult.builder()
                .success(false)
                .errorMessage("Invalid")
                .rowNumber(2)
                .build();
        when(excelValidationService.validateRow(eq(rowData), anyInt())).thenReturn(validationResult);

        ExcelUploadResponse response = excelUploadService.processExcelUpload(multipartFile, "tester");

        assertEquals(0, response.getSuccessRows());
        assertEquals(1, response.getFailedRows());
    }

    @Test
    void testProcessExcelUpload_propertyConversionFails() throws IOException {
        UploadAudit audit = buildAudit();
        when(uploadAuditRepository.save(any())).thenReturn(audit);

        ExcelRowData rowData = new ExcelRowData();
        when(excelProcessorService.processExcelFile(multipartFile)).thenReturn(List.of(rowData));

        UploadAudit.RowResult validationResult = UploadAudit.RowResult.builder()
                .success(true)
                .rowNumber(2)
                .build();
        when(excelValidationService.validateRow(eq(rowData), anyInt())).thenReturn(validationResult);

        when(excelProcessorService.convertToProperty(rowData)).thenThrow(new RuntimeException("Conversion error"));

        ExcelUploadResponse response = excelUploadService.processExcelUpload(multipartFile, "tester");

        assertEquals(0, response.getSuccessRows());
        assertEquals(1, response.getFailedRows());
    }

    @Test
    void testProcessExcelUpload_processorThrowsException() throws IOException {
        UploadAudit audit = buildAudit();
        when(uploadAuditRepository.save(any())).thenReturn(audit);

        when(excelProcessorService.processExcelFile(multipartFile)).thenThrow(new RuntimeException("File corrupt"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> excelUploadService.processExcelUpload(multipartFile, "tester"));

        assertTrue(ex.getMessage().contains(Constants.ERROR_PROCESSING_EXCEL_FILE));
        verify(uploadAuditRepository, atLeast(2)).save(any());
    }

    @Test
    void testGetUploadStatus_found() {
        UploadAudit audit = buildAudit();
        when(uploadAuditRepository.findById(audit.getUploadId())).thenReturn(Optional.of(audit));

        UploadAudit result = excelUploadService.getUploadStatus(audit.getUploadId());

        assertNotNull(result);
        assertEquals(audit.getUploadId(), result.getUploadId());
    }

    @Test
    void testGetUploadStatus_notFound() {
        UUID id = UUID.randomUUID();
        when(uploadAuditRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UploadNotFoundException.class, () -> excelUploadService.getUploadStatus(id));
    }
}
