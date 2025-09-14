package com.tdit.dataprovideservice.service;

import com.tdit.dataprovideservice.dto.ExcelRowData;
import com.tdit.dataprovideservice.constants.Constants;
import com.tdit.dataprovideservice.entity.UploadAudit;
import com.tdit.dataprovideservice.service.Service_Interface.ExcelValidationServiceInterface;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExcelValidationServiceImp implements ExcelValidationServiceInterface {

    private final Validator validator;

    public UploadAudit.RowResult validateRow(ExcelRowData rowData, int rowNumber) {
        List<String> errors = new ArrayList<>();
        List<String> warnings = new ArrayList<>();


        Set<ConstraintViolation<ExcelRowData>> violations = validator.validate(rowData);
        for (ConstraintViolation<ExcelRowData> violation : violations) {
            errors.add(violation.getMessage());
        }

        addBusinessValidations(rowData, errors, warnings);

        boolean success = errors.isEmpty();

        return UploadAudit.RowResult.builder()
                .success(success)
                .errorMessage(errors.isEmpty() ? null : String.join("; ", errors))
                .warningMessage(warnings.isEmpty() ? null : String.join("; ", warnings))
                .rowNumber(rowNumber)
                .build();
    }

    private void addBusinessValidations(ExcelRowData rowData, List<String> errors, List<String> warnings) {
        validateCurrency(rowData.getCurrency(), warnings);
        validateLatitude(rowData.getLatitude(), errors);
        validateLongitude(rowData.getLongitude(), errors);
        validateBasePrice(rowData.getBasePrice(), errors);
        validatePropertyUrl(rowData.getPropertyUrl(), warnings);
        validateHostDetails(rowData.getHostId(), rowData.getHostName(), warnings);
    }

    private void validateCurrency(String currency, List<String> warnings) {
        if (currency == null) return;

        if (!Constants.PREFERRED_CURRENCIES.contains(currency.toUpperCase())) {
            warnings.add(String.format(Constants.WARN_CURRENCY_NOT_PREFERRED, currency));
        }
    }

    private void validateLatitude(String latitude, List<String> errors) {
        validateDoubleValue(latitude, Constants.LAT_MIN, Constants.LAT_MAX,
                Constants.ERROR_LAT_NUMBER, Constants.ERROR_LAT_RANGE, errors);
    }
    private void validateLongitude(String longitude, List<String> errors) {
        validateDoubleValue(longitude, Constants.LNG_MIN, Constants.LNG_MAX,
                Constants.ERROR_LNG_NUMBER, Constants.ERROR_LNG_RANGE, errors);
    }

    private void validateBasePrice(String basePrice, List<String> errors) {
        if (isNullOrBlank(basePrice)) return;

        try {
            double price = Double.parseDouble(basePrice);
            if (price <= Constants.BASE_PRICE_MIN) {
                errors.add(Constants.ERROR_BASE_PRICE_RANGE);
            }
        } catch (NumberFormatException e) {
            errors.add(Constants.ERROR_BASE_PRICE_NUMBER);
        }
    }

    private void validatePropertyUrl(String propertyUrl, List<String> warnings) {
        if (isNullOrBlank(propertyUrl)) return;

        String url = propertyUrl.toLowerCase();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            warnings.add(Constants.WARN_PROPERTY_URL);
        }
    }

    private void validateHostDetails(String hostId, String hostName, List<String> warnings) {
        if (hostId != null && hostName != null &&
                hostId.trim().isEmpty() && !hostName.trim().isEmpty()) {
            warnings.add(Constants.WARN_HOST_ID_EMPTY);
        }
    }

    private void validateDoubleValue(String value, double min, double max,
                                     String numberError, String rangeError, List<String> errors) {
        if (isNullOrBlank(value)) return;

        try {
            double parsed = Double.parseDouble(value);
            if (parsed < min || parsed > max) {
                errors.add(rangeError);
            }
        } catch (NumberFormatException e) {
            errors.add(numberError);
        }
    }

    private boolean isNullOrBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

}
