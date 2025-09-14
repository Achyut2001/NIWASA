package com.tdit.dataprovideservice.dto;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

class ExcelUploadRequestTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        ExcelUploadRequest request = new ExcelUploadRequest();

        MockMultipartFile file = new MockMultipartFile(
                "file", "test.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new byte[]{1, 2, 3}
        );
        request.setFile(file);
        request.setUploadedBy("tester");

        assertEquals("tester", request.getUploadedBy());
        assertNotNull(request.getFile());
        assertEquals("test.xlsx", request.getFile().getOriginalFilename());
    }
}
