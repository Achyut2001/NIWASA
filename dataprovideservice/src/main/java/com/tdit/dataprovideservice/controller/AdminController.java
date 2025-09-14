package com.tdit.dataprovideservice.controller;

import com.tdit.dataprovideservice.constants.Constants;
import com.tdit.dataprovideservice.entity.Property;
import com.tdit.dataprovideservice.entity.Status;
import com.tdit.dataprovideservice.service.PropertyServiceAdminImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class AdminController {

    private final PropertyServiceAdminImp propertyServiceAdmin;

    @PutMapping("/admin/{id}/status")
    public ResponseEntity<String> updatePropertyStatus(
            @PathVariable Long id,
            @RequestParam(required = false) String status) {

        log.info(Constants.LOG_REQUEST_UPDATE_STATUS, id, status);

        if (status == null || status.isBlank() ||
                (!status.equalsIgnoreCase("APPROVED") && !status.equalsIgnoreCase("REJECTED"))) {
            log.warn(Constants.LOG_INVALID_STATUS, status);
            return ResponseEntity.badRequest().body(Constants.ERROR_INVALID_STATUS);
        }

        propertyServiceAdmin.updatePropertyStatus(id, status.toUpperCase());
        log.info(Constants.LOG_STATUS_UPDATED, id, status.toUpperCase());

        return ResponseEntity.ok(Constants.Property_status_updated_to + status.toUpperCase());
    }

    @GetMapping("/properties")
    public ResponseEntity<List<Property>> getPropertiesByStatus(
            @RequestParam("status") String status) {
        if (!status.equalsIgnoreCase(Status.APPROVED.name()) &&
                !status.equalsIgnoreCase(Status.REJECTED.name()) &&
                !status.equalsIgnoreCase(Status.PENDING.name())) {
            return ResponseEntity.badRequest().build();
        }
        List<Property> properties = propertyServiceAdmin.getPropertiesByStatus(status.toUpperCase());
        return ResponseEntity.ok(properties);
    }

}