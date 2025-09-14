package com.tdit.dataprovideservice.dto;


import com.tdit.dataprovideservice.entity.Property_Type;
import com.tdit.dataprovideservice.constants.RegexPatterns;
import com.tdit.dataprovideservice.entity.Status;
import com.tdit.dataprovideservice.constants.ValidationMessages;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelRowData {

    private String propertyId;

    @NotBlank(message = ValidationMessages.PROPERTY_TITLE_REQUIRED)
    @Size(max = 150, message = ValidationMessages.PROPERTY_TITLE_MAX)
    private String propertyTitle;

    @Size(max = 500, message = ValidationMessages.DESCRIPTION_MAX)
    private String description;

    @NotNull(message = ValidationMessages.PROPERTY_TYPE_REQUIRED)
    private Property_Type propertyType;

    @NotBlank(message = ValidationMessages.ADDRESS_LINE1_REQUIRED)
    @Size(max = 200, message = ValidationMessages.ADDRESS_LINE1_MAX)
    private String addressLine1;

    @NotBlank(message = ValidationMessages.CITY_REQUIRED)
    @Size(max = 100, message = ValidationMessages.CITY_MAX)
    private String city;

    @NotBlank(message = ValidationMessages.STATE_REQUIRED)
    @Size(max = 100, message = ValidationMessages.STATE_MAX)
    private String state;

    @NotBlank(message = ValidationMessages.COUNTRY_REQUIRED)
    @Size(max = 100, message = ValidationMessages.COUNTRY_MAX)
    private String country;

    @NotBlank(message = ValidationMessages.PINCODE_REQUIRED)
    @Pattern(regexp = RegexPatterns.PINCODE, message = ValidationMessages.PINCODE_INVALID)
    private String pincode;

    @Pattern(regexp = RegexPatterns.LAT_LONG, message = ValidationMessages.LATITUDE_INVALID)
    private String latitude;

    @Pattern(regexp = RegexPatterns.LAT_LONG, message = ValidationMessages.LONGITUDE_INVALID)
    private String longitude;

    @NotBlank(message = ValidationMessages.HOST_ID_REQUIRED)
    @Pattern(regexp = RegexPatterns.HOST_ID, message = ValidationMessages.HOST_ID_INVALID)
    private String hostId;

    @NotBlank(message = ValidationMessages.HOST_NAME_REQUIRED)
    @Size(max = 100, message = ValidationMessages.HOST_NAME_MAX)
    private String hostName;

    @NotBlank(message = ValidationMessages.HOST_CONTACT_REQUIRED)
    @Pattern(regexp = RegexPatterns.HOST_CONTACT, message = ValidationMessages.HOST_CONTACT_INVALID)
    private String hostContact;

    @NotBlank(message = ValidationMessages.HOST_EMAIL_REQUIRED)
    @Email(message = ValidationMessages.HOST_EMAIL_INVALID)
    @Size(max = 100, message = ValidationMessages.HOST_EMAIL_MAX)
    private String hostEmail;
    @NotBlank(message = ValidationMessages.BASE_PRICE_REQUIRED)
    @Pattern(regexp = RegexPatterns.BASE_PRICE, message = ValidationMessages.BASE_PRICE_INVALID)
    private String basePrice;

    @NotBlank(message = ValidationMessages.CURRENCY_REQUIRED)
    @Pattern(regexp = RegexPatterns.CURRENCY, message = ValidationMessages.CURRENCY_INVALID)
    private String currency;

    @Size(max = 1000, message = ValidationMessages.AMENITIES_MAX)
    private String amenities;

    @Size(max = 500, message = ValidationMessages.PROPERTY_URL_MAX)
    @Pattern(regexp = RegexPatterns.PROPERTY_URL, message = ValidationMessages.PROPERTY_URL_INVALID)
    private String propertyUrl;

    @NotNull(message = ValidationMessages.STATUS_REQUIRED)
    private Status status;

    @Pattern(regexp = RegexPatterns.DATE_TIME, message = ValidationMessages.CREATED_DATE_FORMAT)
    private String createdAt;

    @Pattern(regexp = RegexPatterns.DATE_TIME, message = ValidationMessages.UPDATED_DATE_FORMAT)
    private String updatedAt;

    public List<String> getAmenitiesList() {
        if (amenities == null || amenities.trim().isEmpty()) {
            return new java.util.ArrayList<>();
        }
        String clean = amenities.trim();
        if (clean.startsWith("[") && clean.endsWith("]")) {
            clean = clean.substring(1, clean.length() - 1).replace("\"", "");
        }
        return Arrays.stream(clean.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}
