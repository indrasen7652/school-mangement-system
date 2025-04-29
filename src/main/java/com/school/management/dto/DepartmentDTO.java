package com.school.management.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    Long uid;
    String name;
    Long createdBy;
    LocalDateTime createdOn;
    Long updatedBy;
    LocalDateTime updatedOn;
}
