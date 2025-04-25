package com.school.management.dto.student;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "DTO for Student details with validation")
public class StudentDTO {

    @Schema(description = "Unique ID of the student", example = "101")
    private Long uid;

    private String registrationNumber;

    @NotBlank(message = "User ID is required")
    @Size(min = 4, max = 20, message = "User ID must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "User ID can only contain letters, numbers, and underscores")
    private String userId;

    private String password;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters")
    @Schema(description = "First name of the student", example = "Indrasen")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
    @Schema(description = "Last name of the student", example = "Yadav")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Schema(description = "Email address of the student", example = "indrasen@example.com")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Schema(description = "Phone number of the student", example = "9876543210")
    private String phoneNumber;


    @NotBlank(message = "Class name is required")
    @Schema(description = "Class name or grade", example = "10th Grade")
    private String className;

    @NotBlank(message = "Gender is required")
    @Schema(description = "Gender of the student", example = "Male")
    private String gender;

    @Past(message = "Date of birth must be in the past")
    @Schema(description = "Date of birth of the student", example = "2005-08-15")
    private LocalDate dateOfBirth;

    @NotNull(message = "Active status is required")
    @Schema(description = "Whether the student is currently active", example = "true")
    private Short status;

    @Column(name = "remarks")
    String remarks;

    @Schema(description = "Timestamp when the user record was created", example = "2025-01-01 12:00:00")
    private LocalDateTime createdOn;

    @Schema(description = "User who created the record", example = "admin")
    private Long createdBy;


    @Schema(description = "Timestamp when the user record was last updated", example = "2025-01-01 12:00:00")
    private LocalDateTime updatedOn;

    @Schema(description = "User who last updated the record", example = "admin")
    private Long updatedBy;


    List<AddressDTO> addressDTOList;
}
