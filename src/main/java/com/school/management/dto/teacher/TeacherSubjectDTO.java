package com.school.management.dto.teacher;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherSubjectDTO {

    @Schema(description = "Unique identifier of the teacher subject")
    private Long uid;

    @Schema(description = "Unique identifier of the teacher")
    private Long teacherId;

    @NotBlank(message = "Subject name is required")
    @Schema(description = "Name of the subject assigned to the teacher")
    private String subjectName;

    @Schema(description = "Status of the teacher subject assignment")
    private Short status;

    @Schema(description = "Start date of the teacher's subject assignment")
    private LocalDate startOn;

    @Schema(description = "End date of the teacher's subject assignment")
    private LocalDate endOn;
}
