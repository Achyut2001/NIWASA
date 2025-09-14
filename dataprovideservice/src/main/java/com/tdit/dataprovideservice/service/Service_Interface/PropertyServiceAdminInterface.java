package com.tdit.dataprovideservice.service.Service_Interface;

import com.tdit.dataprovideservice.entity.Property;

import java.util.List;

public interface PropertyServiceAdminInterface {
     void updatePropertyStatus(Long id, String status);
     public List<Property> getPropertiesByStatus(String status);
}
