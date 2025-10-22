package com.tdit.dataprovideservice.constants;

import java.util.List;

public final class Constants {

    public static final String Property_status_updated_to = "Property status updated to ";
    public static final String STATUS_COMPLETED = "COMPLETED";
    public static final String STATUS_FAILED = "FAILED";
    public static final String UPLOAD_NOT_FOUND = "Upload not found: ";
    public static final String ERROR_ROW_EXTRACTION = "Failed to extract row data";
    public static final String ERROR_PROPERTY_CONVERSION = "Error converting to property: ";
    public static final String ERROR_PROCESSING_EXCEL_FILE = "Failed to process Excel file: ";
    public static final String ERROR_FILE_EMPTY = "File is empty";
    public static final String ERROR_FILE_TYPE = "Only .xlsx files are supported";
    public static final String ERROR_FILE_PROCESSING = "Error processing file: ";
    public static final String ERROR_INVALID_STATUS = "Invalid status. Only APPROVED or REJECTED allowed.";
    public static final String MESSAGE_TEMPLATE = "Processed %d rows: %d success, %d failed, %d warnings";
    public static final String LOG_PROCESSING_UPLOAD = "Processing Excel upload: {} by user: {}";
    public static final String LOG_ERROR_UPLOAD = "Error in Excel upload: {}";
    public static final String LOG_ERROR_UPLOAD_STATUS = "Error getting upload status: {}";
    public static final String INVALID_PROPERTY_ID = "Invalid Property ID: {}";
    public static final String INVALID_LATITUDE = "Invalid Latitude: {}";
    public static final String INVALID_LONGITUDE = "Invalid Longitude: {}";
    public static final String INVALID_HOST_ID = "Invalid Host ID: {}";
    public static final String INVALID_BASE_PRICE = "Invalid Base Price: {}";
    public static final List<String> PREFERRED_CURRENCIES = List.of("INR", "USD", "EUR", "GBP", "CAD", "AUD");
    public static final double LAT_MIN = -90;
    public static final double LAT_MAX = 90;
    public static final double LNG_MIN = -180;
    public static final double LNG_MAX = 180;
    public static final double BASE_PRICE_MIN = 0;
    public static final String ERROR_LAT_RANGE = "Latitude must be between -90 and 90";
    public static final String ERROR_LAT_NUMBER = "Latitude must be a valid number";
    public static final String ERROR_LNG_RANGE = "Longitude must be between -180 and 180";
    public static final String ERROR_LNG_NUMBER = "Longitude must be a valid number";
    public static final String ERROR_BASE_PRICE_RANGE = "Base price must be greater than 0";
    public static final String ERROR_BASE_PRICE_NUMBER = "Base price must be a valid number";
    public static final String WARN_CURRENCY_NOT_PREFERRED = "Currency %s is not in preferred list";
    public static final String WARN_PROPERTY_URL = "Property URL should start with http:// or https://";
    public static final String WARN_HOST_ID_EMPTY = "Host ID is empty but Host Name is provided";
    public static final String ERROR_PROPERTY_NOT_FOUND = "Property not found";

    public static final String Date_Format = "yyyy-MM-dd HH:mm:ss";
    public static final String Invalid_date_format_for_field = "Invalid date format for field '";
    public static final String created_At = "createdAt";
    public static final String LOG_REQUEST_UPDATE_STATUS = "Received request to update property status. Property ID: {}, Status: {}";
    public static final String LOG_INVALID_STATUS = "Invalid status value provided: {}";
    public static final String LOG_STATUS_UPDATED = "Successfully updated property ID {} to status {}";
    public static final String Fetching_properties_with_status= "Fetching properties with status: {}";
    public static final String No_properties_Found= "No properties found with status: {}";
    public static final String properties_Found= " properties found with status: {}";
    public static final String LOG_START_PROCESS_EXCEL = "Starting processExcelFile() with file: {}";
    public static final String LOG_SHEET_COUNT = "Workbook has {} sheets";
    public static final String LOG_PROCESSING_SHEET = "Processing sheet: {}";
    public static final String LOG_ROW_PROCESSED = "Processed row {} successfully";
    public static final String LOG_START_CONVERT_PROPERTY = "Starting convertToProperty() for PropertyId: {}";
    public static final String LOG_END_CONVERT_PROPERTY = "Completed convertToProperty() for PropertyId: {}";
    public static final String LOG_COLUMN_INDEX_REASON = "ColumnIndex: {}, Reason: {}";
    public static final String Stared_processExcelUpload_ForFile = "Started processExcelUpload() for file: {} by user: {}";

    public static final String STATUS_PENDING = "PENDING";
    public static final String  f = "PENDING";


    private Constants() {

    }


}
