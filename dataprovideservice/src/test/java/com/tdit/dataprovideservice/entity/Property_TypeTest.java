package com.tdit.dataprovideservice.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Property_TypeTest {

    @Test
    void testFromStringValidExact() {
        assertEquals(Property_Type.APARTMENT, Property_Type.fromString("APARTMENT"));
        assertEquals(Property_Type.VILLA, Property_Type.fromString("VILLA"));
        assertEquals(Property_Type.PG, Property_Type.fromString("PG"));
        assertEquals(Property_Type.HOTEL, Property_Type.fromString("HOTEL"));
        assertEquals(Property_Type.HOSTEL, Property_Type.fromString("HOSTEL"));
    }

    @Test
    void testFromStringValidIgnoreCase() {
        assertEquals(Property_Type.APARTMENT, Property_Type.fromString("apartment"));
        assertEquals(Property_Type.VILLA, Property_Type.fromString("ViLLa"));
    }

    @Test
    void testFromStringWithSpaces() {
        assertEquals(Property_Type.APARTMENT, Property_Type.fromString("  apartment  "));
        assertEquals(Property_Type.VILLA, Property_Type.fromString("  V I L L A "));
    }

    @Test
    void testFromStringInvalidValue() {
        assertNull(Property_Type.fromString("MANSION"));
        assertNull(Property_Type.fromString(""));  // empty string
    }

    @Test
    void testFromStringNullValue() {
        assertNull(Property_Type.fromString(null));
    }
}
