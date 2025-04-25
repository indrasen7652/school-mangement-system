package com.school.management.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Data Transfer Object for Address details.")
public class AddressDTO {

    @Schema(description = "Unique identifier for the address", example = "101")
    private Long uid;

    @Schema(description = "UID of the user to whom this address belongs", example = "501")
    private Long studentUid;

    @Schema(description = "Type of address (e.g., 1 = Permanent, 2 = Temporary)", example = "1")
    private Short addressType;

    @Schema(description = "House or flat number", example = "123A")
    private String houseNo;

    @Schema(description = "Street name or address line", example = "MG Road")
    private String street;

    @Schema(description = "City name", example = "Bhopal")
    private String city;

    @Schema(description = "State name", example = "Madhya Pradesh")
    private String state;

    @Schema(description = "Postal code or ZIP code", example = "462001")
    private String pincode;

    @Schema(description = "Country name", example = "India")
    private String country;

    @Schema(description = "Status whether the address is active", example = "true")
    private Short status;
}
