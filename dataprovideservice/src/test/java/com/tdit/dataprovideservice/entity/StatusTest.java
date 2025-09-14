package com.tdit.dataprovideservice.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void testFromStringValidExact() {
        assertEquals(Status.PENDING, Status.fromString("PENDING"));
        assertEquals(Status.REJECTED, Status.fromString("REJECTED"));
        assertEquals(Status.APPROVED, Status.fromString("APPROVED"));
    }

    @Test
    void testFromStringValidIgnoreCase() {
        assertEquals(Status.PENDING, Status.fromString("pending"));
        assertEquals(Status.REJECTED, Status.fromString("ReJeCtEd"));
        assertEquals(Status.APPROVED, Status.fromString("approved"));
    }

    @Test
    void testFromStringWithSpaces() {
        assertEquals(Status.PENDING, Status.fromString("  PENDING  "));
        assertEquals(Status.APPROVED, Status.fromString("  approved  "));
    }

    @Test
    void testFromStringInvalidValue() {
        assertNull(Status.fromString("IN_PROGRESS"));
        assertNull(Status.fromString(""));  // empty string
    }

    @Test
    void testFromStringNullValue() {
        assertNull(Status.fromString(null));
    }
}
