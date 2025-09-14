package com.tdit.dataprovideservice.service;

import com.tdit.dataprovideservice.entity.Property;
import com.tdit.dataprovideservice.exception.PropertyNotFoundException;
import com.tdit.dataprovideservice.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static com.tdit.dataprovideservice.constants.Constants.ERROR_PROPERTY_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PropertyServiceAdminTest {

    private PropertyRepository propertyRepository;
    private PropertyServiceAdminImp propertyServiceAdmin;

    @BeforeEach
    void setUp() {
        propertyRepository = mock(PropertyRepository.class);
        propertyServiceAdmin = new PropertyServiceAdminImp(propertyRepository);
    }

    @Test
    void testUpdatePropertyStatus_PropertyExists() {
        Property property = new Property();
        property.setPropertyId(1L);
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(propertyRepository.save(any())).thenReturn(property);

        propertyServiceAdmin.updatePropertyStatus(1L, "APPROVED");

        ArgumentCaptor<Property> captor = ArgumentCaptor.forClass(Property.class);
        verify(propertyRepository, times(1)).save(captor.capture());
        assertEquals("APPROVED", captor.getValue().getStatus());
    }

    @Test
    void testUpdatePropertyStatus_PropertyNotFound() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> propertyServiceAdmin.updatePropertyStatus(1L, "APPROVED"));
        assertEquals(ERROR_PROPERTY_NOT_FOUND, ex.getMessage());

        verify(propertyRepository, never()).save(any());
    }



    @Test
    void testGetPropertiesByStatus_Success() {
        // Arrange
        Property property1 = new Property();
        property1.setStatus("APPROVED");

        Property property2 = new Property();
        property2.setStatus("APPROVED");

        when(propertyRepository.findByStatus("APPROVED"))
                .thenReturn(Arrays.asList(property1, property2));

        // Act
        List<Property> result = propertyServiceAdmin.getPropertiesByStatus("APPROVED");

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("APPROVED", result.get(0).getStatus());
        verify(propertyRepository, times(1)).findByStatus("APPROVED");
    }

    @Test
    void testGetPropertiesByStatus_NoPropertiesFound() {
        // Arrange
        when(propertyRepository.findByStatus("REJECTED"))
                .thenReturn(Collections.emptyList());

        // Act + Assert
        PropertyNotFoundException exception = assertThrows(
                PropertyNotFoundException.class,
                () -> propertyServiceAdmin.getPropertiesByStatus("REJECTED")
        );

        assertEquals("No REJECTED properties found", exception.getMessage());
        verify(propertyRepository, times(1)).findByStatus("REJECTED");
    }
}
