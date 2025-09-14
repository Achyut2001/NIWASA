package com.tdit.dataprovideservice.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        Property property = new Property();
        property.setPropertyId(1L);
        property.setPropertyTitle("Luxury Villa");
        property.setDescription("Sea facing luxury villa");
        property.setPropertyType("Villa");
        property.setAddressLine1("Beach Road");
        property.setCity("Goa");
        property.setState("Goa");
        property.setCountry("India");
        property.setPincode("403001");
        property.setLatitude(15.4909);
        property.setLongitude(73.8278);
        property.setHostId(10L);
        property.setHostName("John Doe");
        property.setHostContact("9876543210");
        property.setHostEmail("john@example.com");
        property.setBasePrice(12000.50);
        property.setCurrency("INR");
        property.setAmenities(Arrays.asList("WiFi", "Pool"));
        property.setImageUrl("http://example.com/villa.jpg");
        property.setStatus("AVAILABLE");
        property.setCreatedAt(LocalDateTime.now());
        property.setUpdatedAt(LocalDateTime.now());

        assertEquals("Luxury Villa", property.getPropertyTitle());
        assertEquals("Villa", property.getPropertyType());
        assertEquals("Goa", property.getCity());
        assertEquals("India", property.getCountry());
        assertEquals("9876543210", property.getHostContact());
        assertEquals("INR", property.getCurrency());
        assertTrue(property.getAmenities().contains("Pool"));
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Property property = new Property(
                2L, "Cottage", "Mountain side cottage", "Cottage",
                "Hill Road", "Manali", "HP", "India", "175131",
                32.2396, 77.1887,
                20L, "Jane Doe", "9988776655", "jane@example.com",
                8000.00, "INR",
                Arrays.asList("Heater", "Balcony"),
                "http://example.com/cottage.jpg",
                "BOOKED", now, now
        );

        assertEquals(2L, property.getPropertyId());
        assertEquals("Cottage", property.getPropertyTitle());
        assertEquals("BOOKED", property.getStatus());
        assertEquals("HP", property.getState());
        assertEquals("jane@example.com", property.getHostEmail());
    }

    @Test
    void testBuilderPattern() {
        LocalDateTime now = LocalDateTime.now();

        Property property = Property.builder()
                .propertyId(3L)
                .propertyTitle("Apartment")
                .description("2BHK furnished")
                .propertyType("Apartment")
                .addressLine1("MG Road")
                .city("Bangalore")
                .state("Karnataka")
                .country("India")
                .pincode("560001")
                .latitude(12.9716)
                .longitude(77.5946)
                .hostId(30L)
                .hostName("Ravi Kumar")
                .hostContact("9123456789")
                .hostEmail("ravi@example.com")
                .basePrice(15000.0)
                .currency("INR")
                .amenities(Arrays.asList("Gym", "Parking"))
                .imageUrl("http://example.com/apartment.jpg")
                .status("AVAILABLE")
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertNotNull(property);
        assertEquals("Apartment", property.getPropertyTitle());
        assertEquals("Ravi Kumar", property.getHostName());
        assertTrue(property.getAmenities().contains("Gym"));
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();

        Property property1 = Property.builder()
                .propertyId(5L)
                .propertyTitle("Farmhouse")
                .createdAt(now)
                .build();

        Property property2 = Property.builder()
                .propertyId(5L)
                .propertyTitle("Farmhouse")
                .createdAt(now)
                .build();

        assertEquals(property1, property2);
        assertEquals(property1.hashCode(), property2.hashCode());

        property2.setPropertyTitle("Changed");
        assertNotEquals(property1, property2);
    }

    @Test
    void testToString() {
        Property property = Property.builder()
                .propertyId(100L)
                .propertyTitle("Resort")
                .build();

        String toString = property.toString();
        assertTrue(toString.contains("Resort"));
        assertTrue(toString.contains("100"));
    }
}
