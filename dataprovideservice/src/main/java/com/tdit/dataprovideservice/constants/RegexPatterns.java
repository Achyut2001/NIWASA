package com.tdit.dataprovideservice.constants;

public class RegexPatterns {
    public static final String PINCODE = "\\d{5,10}";
    public static final String LAT_LONG = "^-?\\d+(\\.\\d+)?$";
    public static final String HOST_ID = "\\d+";
    public static final String HOST_CONTACT = "\\d{10,15}";
    public static final String EMAIL = ".+@.+\\..+";
    public static final String BASE_PRICE = "^\\d+(\\.\\d+)?$";
    public static final String CURRENCY = "^(INR|USD|EUR|GBP|CAD|AUD|JPY|CHF|CNY|SGD|NZD|SEK|NOK|DKK|PLN|CZK|HUF|RON|BGN)$";
    public static final String PROPERTY_URL = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$";
    public static final String DATE_TIME = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";

    private RegexPatterns() {

    }
}
