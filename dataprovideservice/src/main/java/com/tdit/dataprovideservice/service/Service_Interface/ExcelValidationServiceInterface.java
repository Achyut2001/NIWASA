package com.tdit.dataprovideservice.service.Service_Interface;

import com.tdit.dataprovideservice.dto.ExcelRowData;
import com.tdit.dataprovideservice.entity.UploadAudit;

import java.util.List;

public interface ExcelValidationServiceInterface {
    UploadAudit.RowResult validateRow(ExcelRowData rowData, int rowNumber);

}
