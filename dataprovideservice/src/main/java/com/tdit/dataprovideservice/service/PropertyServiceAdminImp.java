package com.tdit.dataprovideservice.service;

import com.tdit.dataprovideservice.entity.Property;
import com.tdit.dataprovideservice.entity.Status;
import com.tdit.dataprovideservice.exception.PropertyNotFoundException;
import com.tdit.dataprovideservice.repository.PropertyRepository;
import com.tdit.dataprovideservice.service.Service_Interface.PropertyServiceAdminInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.tdit.dataprovideservice.constants.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PropertyServiceAdminImp implements PropertyServiceAdminInterface {

    private final PropertyRepository propertyRepository;

    public void updatePropertyStatus(Long id, String status) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ERROR_PROPERTY_NOT_FOUND));
        property.setStatus(status);
        propertyRepository.save(property);
    }

    public List<Property> getPropertiesByStatus(String status) {
        log.info(Fetching_properties_with_status, status);
        try {
            Status validStatus = Status.valueOf(status.toUpperCase());
            List<Property> properties = propertyRepository.findByStatus(validStatus.name());
            if (properties.isEmpty()) {
                log.warn(No_properties_Found, validStatus.name());
                throw new PropertyNotFoundException("No " + validStatus.name() + properties_Found);
            }
            log.debug("{} properties found with status: {}", properties.size(), validStatus.name());
            return properties;
        } catch (IllegalArgumentException e) {
            log.error(LOG_INVALID_STATUS, status);
            throw new IllegalArgumentException(LOG_INVALID_STATUS + status);
        }
    }


}
