package com.tdit.dataprovideservice.dto;

import com.tdit.dataprovideservice.entity.Property_Type;
import com.tdit.dataprovideservice.entity.Status;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelRowDataTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        ExcelRowData row = new ExcelRowData();
        row.setPropertyId("1");
        row.setPropertyTitle("Luxury Villa");
        row.setDescription("Sea facing villa");
        row.setPropertyType(Property_Type.VILLA);
        row.setAddressLine1("Beach Road");
        row.setCity("Goa");
        row.setState("Goa");
        row.setCountry("India");
        row.setPincode("403001");
        row.setLatitude("15.4909");
        row.setLongitude("73.8278");
        row.setHostId("100");
        row.setHostName("John Doe");
        row.setHostContact("9876543210");
        row.setHostEmail("john@example.com");
        row.setBasePrice("12000");
        row.setCurrency("INR");
        row.setAmenities("[\"WiFi\",\"Pool\"]");
        row.setPropertyUrl("http://example.com/villa.jpg");
        row.setStatus(Status.APPROVED);
        row.setCreatedAt("2025-09-08T20:00:00");
        row.setUpdatedAt("2025-09-08T21:00:00");

        assertEquals("Luxury Villa", row.getPropertyTitle());
        assertEquals(Property_Type.VILLA, row.getPropertyType());
        assertEquals(Status.APPROVED, row.getStatus());
        assertTrue(row.getAmenitiesList().contains("WiFi"));
    }

    @Test
    void testBuilderPattern() {
        ExcelRowData row = ExcelRowData.builder()
                .propertyId("2")
                .propertyTitle("Apartment")
                .propertyType(Property_Type.APARTMENT)
                .city("Bangalore")
                .state("Karnataka")
                .country("India")
                .pincode("560001")
                .hostId("101")
                .hostName("Jane Doe")
                .hostContact("9123456789")
                .hostEmail("jane@example.com")
                .basePrice("15000")
                .currency("INR")
                .status(Status.PENDING)
                .amenities("Gym, Parking")
                .build();

        assertEquals("Apartment", row.getPropertyTitle());
        assertEquals(Property_Type.APARTMENT, row.getPropertyType());
        assertTrue(row.getAmenitiesList().contains("Gym"));
        assertTrue(row.getAmenitiesList().contains("Parking"));
    }

    @Test
    void testGetAmenitiesListNullOrEmpty() {
        ExcelRowData row = new ExcelRowData();
        row.setAmenities(null);
        assertTrue(row.getAmenitiesList().isEmpty());

        row.setAmenities("");
        assertTrue(row.getAmenitiesList().isEmpty());

        row.setAmenities("   ");
        assertTrue(row.getAmenitiesList().isEmpty());
    }

    @Test
    void testGetAmenitiesListJsonStyle() {
        ExcelRowData row = new ExcelRowData();
        row.setAmenities("[\"WiFi\",\"Pool\",\"Parking\"]");

        List<String> amenities = row.getAmenitiesList();
        assertEquals(3, amenities.size());
        assertTrue(amenities.contains("WiFi"));
        assertTrue(amenities.contains("Pool"));
        assertTrue(amenities.contains("Parking"));
    }

    @Test
    void testGetAmenitiesListCommaSeparated() {
        ExcelRowData row = new ExcelRowData();
        row.setAmenities("WiFi, Pool , Parking ");

        List<String> amenities = row.getAmenitiesList();
        assertEquals(3, amenities.size());
        assertTrue(amenities.contains("WiFi"));
        assertTrue(amenities.contains("Pool"));
        assertTrue(amenities.contains("Parking"));
    }

    @Test
    void testGetAmenitiesListWithEmptyElements() {
        ExcelRowData row = new ExcelRowData();
        row.setAmenities("WiFi,, ,Pool, ,Parking");

        List<String> amenities = row.getAmenitiesList();
        assertEquals(3, amenities.size());
        assertTrue(amenities.contains("WiFi"));
        assertTrue(amenities.contains("Pool"));
        assertTrue(amenities.contains("Parking"));
    }
}
