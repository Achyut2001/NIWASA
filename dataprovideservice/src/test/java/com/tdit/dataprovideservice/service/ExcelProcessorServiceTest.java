package com.tdit.dataprovideservice.service;

import com.tdit.dataprovideservice.dto.ExcelRowData;
import com.tdit.dataprovideservice.entity.Property;
import com.tdit.dataprovideservice.entity.Property_Type;
import com.tdit.dataprovideservice.entity.Status;
import com.tdit.dataprovideservice.exception.InvalidDateFormatException;
import com.tdit.dataprovideservice.exception.InvalidNumberFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ExcelProcessorServiceTest {

    private ExcelProcessorServiceIml service;

    @BeforeEach
    void setUp() {
        service = new ExcelProcessorServiceIml();
    }

    @Test
    void testProcessExcelFile_validFile_returnsRows() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test");
        sheet.createRow(0);
        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue("101");
        row.createCell(1).setCellValue("Luxury Villa");
        row.createCell(19).setCellValue("PENDING");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();

        MockMultipartFile file = new MockMultipartFile(
                "file", "test.xlsx", "application/vnd.ms-excel",
                new ByteArrayInputStream(bos.toByteArray())
        );

        List<ExcelRowData> rows = service.processExcelFile(file);
        assertEquals(1, rows.size());
        assertEquals("101", rows.get(0).getPropertyId());
        assertEquals("Luxury Villa", rows.get(0).getPropertyTitle());
        assertEquals(Status.PENDING, rows.get(0).getStatus());
    }

    @Test
    void testProcessExcelFile_emptyFile_returnsEmptyList() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();

        MockMultipartFile file = new MockMultipartFile("file", new ByteArrayInputStream(bos.toByteArray()));
        List<ExcelRowData> rows = service.processExcelFile(file);
        assertTrue(rows.isEmpty());
    }

    @Test
    void testProcessExcelFile_withInvalidRow_returnsEmptyRowObject() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test");
        sheet.createRow(0);
        Row row = sheet.createRow(1);


        row.createCell(0).setCellFormula("INVALID_FORMULA()");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();

        MockMultipartFile file = new MockMultipartFile("file", new ByteArrayInputStream(bos.toByteArray()));

        List<ExcelRowData> rows = service.processExcelFile(file);

        assertEquals(1, rows.size(), "One row should still be returned");


        String propertyId = rows.get(0).getPropertyId();
        assertTrue(propertyId == null || propertyId.equals("0"),
                "propertyId should be null or '0' for invalid formula cell");
    }



    @Test
    void testConvertToProperty_validRowData() {
        ExcelRowData rowData = new ExcelRowData();
        rowData.setPropertyId("123");
        rowData.setPropertyTitle("Test House");
        rowData.setDescription("Nice house");
        rowData.setPropertyType(Property_Type.HOTEL);
        rowData.setLatitude("12.34");
        rowData.setLongitude("56.78");
        rowData.setHostId("999");
        rowData.setBasePrice("5000");
        rowData.setCurrency("usd");
        rowData.setAmenities("wifi,parking");
        rowData.setPropertyUrl("http://example.com");
        rowData.setStatus(Status.APPROVED);
        rowData.setCreatedAt(LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Property property = service.convertToProperty(rowData);
        assertEquals(123L, property.getPropertyId());
        assertEquals("Test House", property.getPropertyTitle());
        assertEquals("HOTEL", property.getPropertyType());
        assertEquals(12.34, property.getLatitude());
        assertEquals(56.78, property.getLongitude());
        assertEquals(999L, property.getHostId());
        assertEquals(5000.0, property.getBasePrice());
        assertEquals("USD", property.getCurrency());
        assertTrue(property.getAmenities().containsAll(Arrays.asList("wifi", "parking")));
        assertEquals("http://example.com", property.getImageUrl());
        assertEquals("APPROVED", property.getStatus());
    }

    @Test
    void testConvertToProperty_invalidLatitude_throwsException() {
        ExcelRowData rowData = new ExcelRowData();
        rowData.setLatitude("notANumber");
        assertThrows(InvalidNumberFormatException.class, () -> service.convertToProperty(rowData));
    }

    @Test
    void testConvertToProperty_invalidLongitude_catchesException() {
        ExcelRowData rowData = new ExcelRowData();
        rowData.setLongitude("badNumber");
        Property property = service.convertToProperty(rowData);
        assertNull(property.getLongitude());
    }

    @Test
    void testConvertToProperty_invalidHostId_catchesException() {
        ExcelRowData rowData = new ExcelRowData();
        rowData.setHostId("NaN");
        Property property = service.convertToProperty(rowData);
        assertNull(property.getHostId());
    }

    @Test
    void testConvertToProperty_invalidBasePrice_catchesException() {
        ExcelRowData rowData = new ExcelRowData();
        rowData.setBasePrice("oops");
        Property property = service.convertToProperty(rowData);
        assertNull(property.getBasePrice());
    }

    @Test
    void testConvertToProperty_invalidDateFormat_throwsException() {
        ExcelRowData rowData = new ExcelRowData();
        rowData.setCreatedAt("invalid-date");
        assertThrows(InvalidDateFormatException.class, () -> service.convertToProperty(rowData));
    }

    @Test
    void testConvertToProperty_blankCreatedAt_setsNow() {
        ExcelRowData rowData = new ExcelRowData();
        rowData.setCreatedAt("");
        Property property = service.convertToProperty(rowData);
        assertNotNull(property.getCreatedAt());
    }



    @Test
    void testParseCellToString_stringAndNumericAndBooleanAndBlank() throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("Hello");
        row.createCell(1).setCellValue(123.0);
        row.createCell(2).setCellValue(12.34);
        row.createCell(3).setCellValue(true);
        row.createCell(4);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        wb.write(bos);
        wb.close();

        MockMultipartFile file = new MockMultipartFile("file", new ByteArrayInputStream(bos.toByteArray()));
        List<ExcelRowData> rows = service.processExcelFile(file);
        assertNotNull(rows);
    }



    @Test
    void testStatusFromString_valid() {
        assertEquals(Status.PENDING, Status.fromString("PENDING"));
        assertEquals(Status.APPROVED, Status.fromString("approved"));
        assertEquals(Status.REJECTED, Status.fromString(" Rejected "));
    }

    @Test
    void testStatusFromString_invalidAndNull() {
        assertNull(Status.fromString("UNKNOWN"));
        assertNull(Status.fromString(""));
        assertNull(Status.fromString(null));
    }

    @Test
    void testPropertyTypeFromString_valid() {
        assertEquals(Property_Type.HOTEL, Property_Type.fromString("HOTEL"));
        assertEquals(Property_Type.APARTMENT, Property_Type.fromString("apartment"));
    }

    @Test
    void testPropertyTypeFromString_invalidAndNull() {
        assertNull(Property_Type.fromString("UNKNOWN"));
        assertNull(Property_Type.fromString(""));
        assertNull(Property_Type.fromString(null));
    }


}
