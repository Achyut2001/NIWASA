package com.tdit.dataprovideservice.service.Service_Interface;

import com.tdit.dataprovideservice.dto.ExcelRowData;
import com.tdit.dataprovideservice.entity.Property;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ExcelProcessorServiceInterface {
    List<ExcelRowData> processExcelFile(MultipartFile file) throws IOException;
   Property convertToProperty(ExcelRowData rowData);

}
