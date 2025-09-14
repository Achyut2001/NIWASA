package com.tdit.dataprovideservice.service;

import com.tdit.dataprovideservice.dto.ExcelRowData;
import com.tdit.dataprovideservice.entity.Property;
import com.tdit.dataprovideservice.entity.Property_Type;
import com.tdit.dataprovideservice.entity.Status;
import com.tdit.dataprovideservice.exception.InvalidDateFormatException;
import com.tdit.dataprovideservice.exception.InvalidNumberFormatException;
import com.tdit.dataprovideservice.service.Service_Interface.ExcelProcessorServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.tdit.dataprovideservice.constants.Constants.*;
@Slf4j
@Service
public class ExcelProcessorServiceIml implements ExcelProcessorServiceInterface {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(Date_Format);

    @Override
    public List<ExcelRowData> processExcelFile(MultipartFile file) throws IOException {
        List<ExcelRowData> rows = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            int sheets = workbook.getNumberOfSheets();

            for (int i = 0; i < sheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                if (sheet == null) continue;

                for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (row == null) continue;

                    try {
                        ExcelRowData rowData = extractRowData(row);
                        rows.add(rowData);
                    } catch (Exception e) {
                        log.warn("Error processing row {}: {}", rowIndex, e.getMessage());
                        ExcelRowData emptyRow = new ExcelRowData();
                        rows.add(emptyRow);
                    }
                }
            }
        }

        return rows;
    }

    private ExcelRowData extractRowData(Row row) {
        ExcelRowData data = new ExcelRowData();

        data.setPropertyId(parseCellToString(row.getCell(0)));
        data.setPropertyTitle(parseCellToString(row.getCell(1)));
        data.setDescription(parseCellToString(row.getCell(2)));
        data.setPropertyType(Property_Type.fromString(parseCellToString(row.getCell(3))));
        data.setAddressLine1(parseCellToString(row.getCell(4)));
        data.setCity(parseCellToString(row.getCell(5)));
        data.setState(parseCellToString(row.getCell(6)));
        data.setCountry(parseCellToString(row.getCell(7)));
        data.setPincode(parseCellToString(row.getCell(8)));
        data.setLatitude(parseCellToString(row.getCell(9)));
        data.setLongitude(parseCellToString(row.getCell(10)));
        data.setHostId(parseCellToString(row.getCell(11)));
        data.setHostName(parseCellToString(row.getCell(12)));
        data.setHostContact(parseCellToString(row.getCell(13)));
        data.setHostEmail(parseCellToString(row.getCell(14)));
        data.setBasePrice(parseCellToString(row.getCell(15)));
        data.setCurrency(parseCellToString(row.getCell(16)));
        data.setAmenities(parseCellToString(row.getCell(17)));
        data.setPropertyUrl(parseCellToString(row.getCell(18)));
        data.setStatus(Status.fromString(parseCellToString(row.getCell(19))));
        data.setCreatedAt(parseCellToString(row.getCell(20)));
        data.setUpdatedAt(parseCellToString(row.getCell(21)));

        return data;
    }

    private String parseCellToString(Cell cell) {
        if (cell == null) return null;

        try {
            return switch (cell.getCellType()) {
                case STRING -> cell.getStringCellValue().trim();
                case NUMERIC -> DateUtil.isCellDateFormatted(cell)
                        ? cell.getLocalDateTimeCellValue().format(DATE_FORMATTER)
                        : (cell.getNumericCellValue() == Math.floor(cell.getNumericCellValue())
                        ? String.valueOf((long) cell.getNumericCellValue())
                        : String.valueOf(cell.getNumericCellValue()));
                case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
                case FORMULA -> evaluateFormulaCell(cell);
                case BLANK -> null;
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }

    private String evaluateFormulaCell(Cell cell) {
        try {
            double val = cell.getNumericCellValue();
            return (val == Math.floor(val)) ? String.valueOf((long) val) : String.valueOf(val);
        } catch (Exception e) {
            try {
                return cell.getStringCellValue().trim();
            } catch (Exception ex) {
                return null;
            }
        }
    }

    @Override
    public Property convertToProperty(ExcelRowData rowData) {
        Property property = new Property();

        parseLongSafe(rowData.getPropertyId(), INVALID_PROPERTY_ID, property::setPropertyId);
        property.setPropertyTitle(rowData.getPropertyTitle());
        property.setDescription(rowData.getDescription());

        if (rowData.getPropertyType() != null) {
            property.setPropertyType(rowData.getPropertyType().name());
        }

        property.setAddressLine1(rowData.getAddressLine1());
        property.setCity(rowData.getCity());
        property.setState(rowData.getState());
        property.setCountry(rowData.getCountry());
        property.setPincode(rowData.getPincode());

        parseDoubleStrict(rowData.getLatitude(), INVALID_LATITUDE, property::setLatitude);
        parseDoubleSafe(rowData.getLongitude(), INVALID_LONGITUDE, property::setLongitude);
        parseLongSafe(rowData.getHostId(), INVALID_HOST_ID, property::setHostId);

        property.setHostName(rowData.getHostName());
        property.setHostContact(rowData.getHostContact());
        property.setHostEmail(rowData.getHostEmail());
        parseDoubleSafe(rowData.getBasePrice(), INVALID_BASE_PRICE, property::setBasePrice);

        if (rowData.getCurrency() != null) {
            property.setCurrency(rowData.getCurrency().toUpperCase());
        }

        property.setAmenities(rowData.getAmenitiesList());
        property.setImageUrl(rowData.getPropertyUrl());

        if (rowData.getStatus() != null) {
            property.setStatus(rowData.getStatus().name());
        }

        LocalDateTime now = LocalDateTime.now();
        if (rowData.getCreatedAt() != null && !rowData.getCreatedAt().trim().isEmpty()) {
            try {
                property.setCreatedAt(LocalDateTime.parse(rowData.getCreatedAt(), DATE_FORMATTER));
            } catch (Exception e) {
                throw new InvalidDateFormatException(created_At, rowData.getCreatedAt(), e);
            }
        } else {
            property.setCreatedAt(now);
        }
        property.setUpdatedAt(now);

        return property;
    }

    private void parseLongSafe(String value, String logMessage, java.util.function.Consumer<Long> setter) {
        if (value != null && !value.trim().isEmpty()) {
            try {
                setter.accept(Long.parseLong(value));
            } catch (NumberFormatException e) {
                log.warn("{}: {}", logMessage, value);
                setter.accept(null);
            }
        } else {
            setter.accept(null);
        }
    }


    private void parseDoubleSafe(String value, String logMessage, Consumer<Double> setter) {
        if (value != null && !value.trim().isEmpty()) {
            try {
                setter.accept(Double.parseDouble(value));
            } catch (Exception e) {
                log.warn(logMessage, value);
            }
        }
    }

    private void parseDoubleStrict(String value, String errorMessage, Consumer<Double> setter) {
        if (value != null && !value.trim().isEmpty()) {
            try {
                setter.accept(Double.parseDouble(value));
            } catch (Exception e) {
                throw new InvalidNumberFormatException(errorMessage, value, e);
            }
        }
    }
}
