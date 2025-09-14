package com.tdit.dataprovideservice.controller;

import com.tdit.dataprovideservice.dto.ExcelUploadResponse;
import com.tdit.dataprovideservice.constants.Constants;
import com.tdit.dataprovideservice.entity.UploadAudit;
import com.tdit.dataprovideservice.service.ExcelUploadServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/excel")
public class ExcelUploadController {

    private final ExcelUploadServiceImp excelUploadService;


    @PostMapping("/upload")
    public ResponseEntity<ExcelUploadResponse> uploadExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "uploadedBy", defaultValue = "system") String uploadedBy) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ExcelUploadResponse.builder()
                                .status(Constants.STATUS_FAILED)
                                .message(Constants.ERROR_FILE_EMPTY)
                                .build());
            }
            if (!file.getOriginalFilename().endsWith(".xlsx")) {
                return ResponseEntity.badRequest()
                        .body(ExcelUploadResponse.builder()
                                .status(Constants.STATUS_FAILED)
                                .message(Constants.ERROR_FILE_TYPE)
                                .build());
            }

            log.info(Constants.LOG_PROCESSING_UPLOAD, file.getOriginalFilename(), uploadedBy);
            ExcelUploadResponse response = excelUploadService.processExcelUpload(file, uploadedBy);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error(Constants.LOG_ERROR_UPLOAD, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ExcelUploadResponse.builder()
                            .status(Constants.STATUS_FAILED)
                            .message(Constants.ERROR_FILE_PROCESSING + e.getMessage())
                            .build());
        }
    }

    @GetMapping("/status/{uploadId}")
    public ResponseEntity<UploadAudit> getUploadStatus(@PathVariable UUID uploadId) {
        try {
            UploadAudit audit = excelUploadService.getUploadStatus(uploadId);
            return ResponseEntity.ok(audit);
        } catch (Exception e) {
            log.error(Constants.LOG_ERROR_UPLOAD_STATUS, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
