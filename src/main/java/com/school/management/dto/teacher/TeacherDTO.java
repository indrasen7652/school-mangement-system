package com.school.management.dto.teacher;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {

    @Schema(description = "Unique identifier of the teacher")
    private Long uid;

    @Schema(description = "Registration number of the teacher")
    private String registrationNumber;

    @NotBlank(message = "User ID is required")
    @Size(min = 4, max = 20, message = "User ID must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "User ID can only contain letters, numbers, and underscores")
    private String userId;

    @Schema(description = "Password for the teacher's login")
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

    @NotBlank(message = "Gender is required")
    @Schema(description = "Gender of the teacher")
    private String gender;

    @Schema(description = "Date when the teacher joined")
    private LocalDate dateOfJoining;

    @Schema(description = "Status of the teacher, where 1 indicates Active and 2 indicates DeActive")
    private Short status;

    @Schema(description = "remarks")
    String remarks;

    @Schema(description = "Timestamp when the teacher record was created")
    private LocalDateTime createdOn;

    @Schema(description = "Username or identifier of the user who created the teacher record")
    private Long createdBy;

    @Schema(description = "Timestamp when the teacher record was last updated")
    private LocalDateTime updatedOn;

    @Schema(description = "User ID of the person who last updated the teacher record")
    private Long updatedBy;

    @Schema(description = "Teacher All Subject")
    List<TeacherSubjectDTO> teacherSubjectDTOList;

    @Schema(description = "Teacher All Address")
    List<TeacherAddressDTO> teacherAddressDTOList;
}
