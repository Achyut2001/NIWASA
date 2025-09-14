package com.tdit.dataprovideservice.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidationMessagesTest {

    private void invokePrivateConstructor() throws Exception {
        Constructor<ValidationMessages> constructor = ValidationMessages.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    void testValidationMessages() throws Exception {
        invokePrivateConstructor();

        assertEquals("Property title is mandatory", ValidationMessages.PROPERTY_TITLE_REQUIRED);
        assertEquals("Property title must be max 150 characters", ValidationMessages.PROPERTY_TITLE_MAX);
        assertEquals("Description must be max 500 characters", ValidationMessages.DESCRIPTION_MAX);
        assertEquals("Property type is mandatory", ValidationMessages.PROPERTY_TYPE_REQUIRED);

        assertEquals("Address Line 1 is mandatory", ValidationMessages.ADDRESS_LINE1_REQUIRED);
        assertEquals("Address Line 1 must be max 200 characters", ValidationMessages.ADDRESS_LINE1_MAX);
        assertEquals("City is mandatory", ValidationMessages.CITY_REQUIRED);
        assertEquals("City must be max 100 characters", ValidationMessages.CITY_MAX);
        assertEquals("State is mandatory", ValidationMessages.STATE_REQUIRED);
        assertEquals("State must be max 100 characters", ValidationMessages.STATE_MAX);
        assertEquals("Country is mandatory", ValidationMessages.COUNTRY_REQUIRED);
        assertEquals("Country must be max 100 characters", ValidationMessages.COUNTRY_MAX);
        assertEquals("Pincode is mandatory", ValidationMessages.PINCODE_REQUIRED);
        assertEquals("Pincode must be 5-10 digits", ValidationMessages.PINCODE_INVALID);

        assertEquals("Latitude must be a valid number", ValidationMessages.LATITUDE_INVALID);
        assertEquals("Longitude must be a valid number", ValidationMessages.LONGITUDE_INVALID);

        assertEquals("Host ID is mandatory", ValidationMessages.HOST_ID_REQUIRED);
        assertEquals("Host ID must be a valid number", ValidationMessages.HOST_ID_INVALID);
        assertEquals("Host name is mandatory", ValidationMessages.HOST_NAME_REQUIRED);
        assertEquals("Host name must be max 100 characters", ValidationMessages.HOST_NAME_MAX);
        assertEquals("Host contact is mandatory", ValidationMessages.HOST_CONTACT_REQUIRED);
        assertEquals("Host contact must be 10-15 digits", ValidationMessages.HOST_CONTACT_INVALID);
        assertEquals("Host email is mandatory", ValidationMessages.HOST_EMAIL_REQUIRED);
        assertEquals("Invalid email format", ValidationMessages.HOST_EMAIL_INVALID);
        assertEquals("Host email must be max 100 characters", ValidationMessages.HOST_EMAIL_MAX);

        assertEquals("Base price is mandatory", ValidationMessages.BASE_PRICE_REQUIRED);
        assertEquals("Base price must be a valid number", ValidationMessages.BASE_PRICE_INVALID);
        assertEquals("Currency is mandatory", ValidationMessages.CURRENCY_REQUIRED);
        assertEquals("Currency must be a valid ISO currency code", ValidationMessages.CURRENCY_INVALID);

        assertEquals("Amenities must be max 1000 characters", ValidationMessages.AMENITIES_MAX);
        assertEquals("Property URL must be max 500 characters", ValidationMessages.PROPERTY_URL_MAX);
        assertEquals("Property URL format may be invalid", ValidationMessages.PROPERTY_URL_INVALID);
        assertEquals("Status is mandatory", ValidationMessages.STATUS_REQUIRED);
        assertEquals("Created date must be in format: yyyy-MM-dd HH:mm:ss", ValidationMessages.CREATED_DATE_FORMAT);
        assertEquals("Updated date must be in format: yyyy-MM-dd HH:mm:ss", ValidationMessages.UPDATED_DATE_FORMAT);
    }
}
