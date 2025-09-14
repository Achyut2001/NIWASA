package com.tdit.dataprovideservice.constants;

public class ValidationMessages {

    private ValidationMessages() {
    }

    public static final String PROPERTY_TITLE_REQUIRED = "Property title is mandatory";
    public static final String PROPERTY_TITLE_MAX = "Property title must be max 150 characters";
    public static final String DESCRIPTION_MAX = "Description must be max 500 characters";
    public static final String PROPERTY_TYPE_REQUIRED = "Property type is mandatory";
    public static final String ADDRESS_LINE1_REQUIRED = "Address Line 1 is mandatory";
    public static final String ADDRESS_LINE1_MAX = "Address Line 1 must be max 200 characters";
    public static final String CITY_REQUIRED = "City is mandatory";
    public static final String CITY_MAX = "City must be max 100 characters";
    public static final String STATE_REQUIRED = "State is mandatory";
    public static final String STATE_MAX = "State must be max 100 characters";
    public static final String COUNTRY_REQUIRED = "Country is mandatory";
    public static final String COUNTRY_MAX = "Country must be max 100 characters";
    public static final String PINCODE_REQUIRED = "Pincode is mandatory";
    public static final String PINCODE_INVALID = "Pincode must be 5-10 digits";
    public static final String LATITUDE_INVALID = "Latitude must be a valid number";
    public static final String LONGITUDE_INVALID = "Longitude must be a valid number";
    public static final String HOST_ID_REQUIRED = "Host ID is mandatory";
    public static final String HOST_ID_INVALID = "Host ID must be a valid number";
    public static final String HOST_NAME_REQUIRED = "Host name is mandatory";
    public static final String HOST_NAME_MAX = "Host name must be max 100 characters";
    public static final String HOST_CONTACT_REQUIRED = "Host contact is mandatory";
    public static final String HOST_CONTACT_INVALID = "Host contact must be 10-15 digits";
    public static final String HOST_EMAIL_REQUIRED = "Host email is mandatory";
    public static final String HOST_EMAIL_INVALID = "Invalid email format";
    public static final String HOST_EMAIL_MAX = "Host email must be max 100 characters";
    public static final String BASE_PRICE_REQUIRED = "Base price is mandatory";
    public static final String BASE_PRICE_INVALID = "Base price must be a valid number";
    public static final String CURRENCY_REQUIRED = "Currency is mandatory";
    public static final String CURRENCY_INVALID = "Currency must be a valid ISO currency code";
    public static final String AMENITIES_MAX = "Amenities must be max 1000 characters";
    public static final String PROPERTY_URL_MAX = "Property URL must be max 500 characters";
    public static final String PROPERTY_URL_INVALID = "Property URL format may be invalid";
    public static final String STATUS_REQUIRED = "Status is mandatory";
    public static final String CREATED_DATE_FORMAT = "Created date must be in format: yyyy-MM-dd HH:mm:ss";
    public static final String UPDATED_DATE_FORMAT = "Updated date must be in format: yyyy-MM-dd HH:mm:ss";
}
