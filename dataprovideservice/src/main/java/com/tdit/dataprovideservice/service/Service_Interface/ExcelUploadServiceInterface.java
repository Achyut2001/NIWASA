package com.tdit.dataprovideservice.service.Service_Interface;

import com.tdit.dataprovideservice.dto.ExcelUploadResponse;
import com.tdit.dataprovideservice.entity.UploadAudit;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ExcelUploadServiceInterface {
    ExcelUploadResponse processExcelUpload(MultipartFile file, String uploadedBy);
    UploadAudit getUploadStatus(UUID uploadId);
}
