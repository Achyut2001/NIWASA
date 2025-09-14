package com.tdit.dataprovideservice.service;

import com.tdit.dataprovideservice.constants.Constants;
import com.tdit.dataprovideservice.dto.ExcelRowData;
import com.tdit.dataprovideservice.entity.UploadAudit;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExcelValidationServiceTest {

    private Validator validator;
    private ExcelValidationServiceImp excelValidationService;

    @BeforeEach
    void setUp() {
        validator = mock(Validator.class);
        excelValidationService = new ExcelValidationServiceImp(validator);
    }

    private ExcelRowData buildRow() {
        return ExcelRowData.builder()
                .currency("USD")
                .latitude("45")
                .longitude("90")
                .basePrice("100")
                .propertyUrl("https://example.com")
                .hostId("123")
                .hostName("John")
                .build();
    }

    @Test
    void testValidRow_noErrorsOrWarnings() {
        when(validator.validate(any())).thenReturn(Collections.emptySet());

        ExcelRowData row = buildRow();
        UploadAudit.RowResult result = excelValidationService.validateRow(row, 2);

        assertTrue(result.isSuccess());
        assertNull(result.getErrorMessage());
        assertNull(result.getWarningMessage());
    }

    @Test
    void testCurrencyNotPreferred_addsWarning() {
        when(validator.validate(any())).thenReturn(Collections.emptySet());

        ExcelRowData row = buildRow();
        row.setCurrency("XYZ"); // not preferred
        UploadAudit.RowResult result = excelValidationService.validateRow(row, 2);

        assertTrue(result.isSuccess());
        assertNotNull(result.getWarningMessage());
        assertTrue(result.getWarningMessage().contains("Currency XYZ is not in preferred list"));
    }

    @Test
    void testLatitudeOutOfRange_addsError() {
        when(validator.validate(any())).thenReturn(Collections.emptySet());

        ExcelRowData row = buildRow();
        row.setLatitude("200"); // invalid
        UploadAudit.RowResult result = excelValidationService.validateRow(row, 2);

        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessage().contains(Constants.ERROR_LAT_RANGE));
    }

    @Test
    void testLatitudeNotNumber_addsError() {
        when(validator.validate(any())).thenReturn(Collections.emptySet());

        ExcelRowData row = buildRow();
        row.setLatitude("abc");
        UploadAudit.RowResult result = excelValidationService.validateRow(row, 2);

        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessage().contains(Constants.ERROR_LAT_NUMBER));
    }

    @Test
    void testLongitudeOutOfRange_addsError() {
        when(validator.validate(any())).thenReturn(Collections.emptySet());

        ExcelRowData row = buildRow();
        row.setLongitude("200");
        UploadAudit.RowResult result = excelValidationService.validateRow(row, 2);

        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessage().contains(Constants.ERROR_LNG_RANGE));
    }

    @Test
    void testLongitudeNotNumber_addsError() {
        when(validator.validate(any())).thenReturn(Collections.emptySet());

        ExcelRowData row = buildRow();
        row.setLongitude("xyz");
        UploadAudit.RowResult result = excelValidationService.validateRow(row, 2);

        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessage().contains(Constants.ERROR_LNG_NUMBER));
    }

    @Test
    void testBasePriceBelowMin_addsError() {
        when(validator.validate(any())).thenReturn(Collections.emptySet());

        ExcelRowData row = buildRow();
        row.setBasePrice("0"); // below min
        UploadAudit.RowResult result = excelValidationService.validateRow(row, 2);

        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessage().contains(Constants.ERROR_BASE_PRICE_RANGE));
    }

    @Test
    void testBasePriceNotNumber_addsError() {
        when(validator.validate(any())).thenReturn(Collections.emptySet());

        ExcelRowData row = buildRow();
        row.setBasePrice("abc");
        UploadAudit.RowResult result = excelValidationService.validateRow(row, 2);

        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessage().contains(Constants.ERROR_BASE_PRICE_NUMBER));
    }

    @Test
    void testPropertyUrlWithoutHttp_addsWarning() {
        when(validator.validate(any())).thenReturn(Collections.emptySet());

        ExcelRowData row = buildRow();
        row.setPropertyUrl("example.com");
        UploadAudit.RowResult result = excelValidationService.validateRow(row, 2);

        assertTrue(result.isSuccess());
        assertNotNull(result.getWarningMessage());
        assertTrue(result.getWarningMessage().contains(Constants.WARN_PROPERTY_URL));
    }

    @Test
    void testHostIdEmptyWithHostName_addsWarning() {
        when(validator.validate(any())).thenReturn(Collections.emptySet());

        ExcelRowData row = buildRow();
        row.setHostId("");
        row.setHostName("SomeName");
        UploadAudit.RowResult result = excelValidationService.validateRow(row, 2);

        assertTrue(result.isSuccess());
        assertNotNull(result.getWarningMessage());
        assertTrue(result.getWarningMessage().contains(Constants.WARN_HOST_ID_EMPTY));
    }


    @Test
    void testValidationViolationFromBeanValidator() {
        // 👇 Put your snippet here
        @SuppressWarnings("unchecked")
        ConstraintViolation<ExcelRowData> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("Bean validation failed");

        Set<ConstraintViolation<ExcelRowData>> violations = Collections.singleton(violation);

        // 👇 Cast needed because Mockito struggles with generics
        when(validator.validate(any(ExcelRowData.class)))
                .thenReturn((Set) violations);

        ExcelRowData row = buildRow();
        UploadAudit.RowResult result = excelValidationService.validateRow(row, 2);

        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessage().contains("Bean validation failed"));
    }

}
