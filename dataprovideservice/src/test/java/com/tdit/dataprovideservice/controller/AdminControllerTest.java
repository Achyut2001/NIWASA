package com.tdit.dataprovideservice.controller;

import com.tdit.dataprovideservice.constants.Constants;
import com.tdit.dataprovideservice.service.PropertyServiceAdminImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyServiceAdminImp propertyServiceAdmin;


    @Test
    void testUpdatePropertyStatus_Approved() throws Exception {
        mockMvc.perform(put("/admin/1/status")
                        .param("status", "APPROVED"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(Constants.Property_status_updated_to + "APPROVED")));

        verify(propertyServiceAdmin, times(1)).updatePropertyStatus(1L, "APPROVED");
    }

    @Test
    void testUpdatePropertyStatus_Rejected() throws Exception {
        mockMvc.perform(put("/admin/2/status")
                        .param("status", "REJECTED"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(Constants.Property_status_updated_to + "REJECTED")));

        verify(propertyServiceAdmin, times(1)).updatePropertyStatus(2L, "REJECTED");
    }

    @Test
    void testUpdatePropertyStatus_NullStatus() throws Exception {
        mockMvc.perform(put("/admin/3/status"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Constants.ERROR_INVALID_STATUS));

        verify(propertyServiceAdmin, times(0)).updatePropertyStatus(anyLong(), anyString());
    }

    @Test
    void testUpdatePropertyStatus_BlankStatus() throws Exception {
        mockMvc.perform(put("/admin/4/status")
                        .param("status", "   "))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Constants.ERROR_INVALID_STATUS));

        verify(propertyServiceAdmin, times(0)).updatePropertyStatus(anyLong(), anyString());
    }

    @Test
    void testUpdatePropertyStatus_InvalidStatus() throws Exception {
        mockMvc.perform(put("/admin/5/status")
                        .param("status", "PENDING"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Constants.ERROR_INVALID_STATUS));

        verify(propertyServiceAdmin, times(0)).updatePropertyStatus(anyLong(), anyString());
    }





}
