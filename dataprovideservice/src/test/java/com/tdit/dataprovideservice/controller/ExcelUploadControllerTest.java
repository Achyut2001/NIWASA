package com.tdit.dataprovideservice.controller;

import com.tdit.dataprovideservice.constants.Constants;
import com.tdit.dataprovideservice.dto.ExcelUploadResponse;
import com.tdit.dataprovideservice.entity.UploadAudit;
import com.tdit.dataprovideservice.service.ExcelUploadServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExcelUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExcelUploadServiceImp excelUploadService;

    // ==================== UPLOAD ENDPOINT TESTS ====================

    @Test
    void testUploadExcel_EmptyFile() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "test.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new byte[0]);

        mockMvc.perform(multipart("/api/excel/upload")
                        .file(emptyFile))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(Constants.STATUS_FAILED))
                .andExpect(jsonPath("$.message").value(Constants.ERROR_FILE_EMPTY));
    }

    @Test
    void testUploadExcel_InvalidFileType() throws Exception {
        MockMultipartFile invalidFile = new MockMultipartFile("file", "test.txt",
                MediaType.TEXT_PLAIN_VALUE, "dummy".getBytes());

        mockMvc.perform(multipart("/api/excel/upload")
                        .file(invalidFile))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(Constants.STATUS_FAILED))
                .andExpect(jsonPath("$.message").value(Constants.ERROR_FILE_TYPE));
    }

    @Test
    void testUploadExcel_Success() throws Exception {
        MockMultipartFile validFile = new MockMultipartFile("file", "test.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "dummy".getBytes());

        ExcelUploadResponse mockResponse = ExcelUploadResponse.builder()
                .status(Constants.STATUS_COMPLETED)
                .message("Uploaded successfully")
                .build();

        when(excelUploadService.processExcelUpload(any(), anyString()))
                .thenReturn(mockResponse);

        mockMvc.perform(multipart("/api/excel/upload")
                        .file(validFile)
                        .param("uploadedBy", "tester"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.STATUS_COMPLETED))
                .andExpect(jsonPath("$.message").value("Uploaded successfully"));
    }

    @Test
    void testUploadExcel_Success_DefaultUploadedBy() throws Exception {
        // test default value of uploadedBy
        MockMultipartFile validFile = new MockMultipartFile("file", "test.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "dummy".getBytes());

        ExcelUploadResponse mockResponse = ExcelUploadResponse.builder()
                .status(Constants.STATUS_COMPLETED)
                .message("Uploaded successfully")
                .build();

        when(excelUploadService.processExcelUpload(any(), anyString()))
                .thenReturn(mockResponse);

        mockMvc.perform(multipart("/api/excel/upload")
                        .file(validFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.STATUS_COMPLETED))
                .andExpect(jsonPath("$.message").value("Uploaded successfully"));
    }

    @Test
    void testUploadExcel_Exception() throws Exception {
        MockMultipartFile validFile = new MockMultipartFile("file", "test.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "dummy".getBytes());

        when(excelUploadService.processExcelUpload(any(), anyString()))
                .thenThrow(new RuntimeException("Processing failed"));

        mockMvc.perform(multipart("/api/excel/upload")
                        .file(validFile)
                        .param("uploadedBy", "tester"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(Constants.STATUS_FAILED))
                .andExpect(jsonPath("$.message", containsString(Constants.ERROR_FILE_PROCESSING)))
                .andExpect(jsonPath("$.message", containsString("Processing failed")));
    }

    // ==================== STATUS CHECK ENDPOINT TESTS ====================

    @Test
    void testGetUploadStatus_Success() throws Exception {
        UUID uploadId = UUID.randomUUID();
        UploadAudit audit = new UploadAudit();
        audit.setUploadId(uploadId);

        when(excelUploadService.getUploadStatus(uploadId)).thenReturn(audit);

        mockMvc.perform(get("/api/excel/status/{uploadId}", uploadId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uploadId").value(uploadId.toString()));
    }

    @Test
    void testGetUploadStatus_NotFound() throws Exception {
        UUID uploadId = UUID.randomUUID();
        doThrow(new RuntimeException("Not found")).when(excelUploadService).getUploadStatus(uploadId);

        mockMvc.perform(get("/api/excel/status/{uploadId}", uploadId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }
}
