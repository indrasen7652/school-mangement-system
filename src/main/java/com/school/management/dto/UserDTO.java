package com.school.management.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long uid;
    private String name;
    private String userId;
    private Short role;
    private String password;
    private Short status;
    private LocalDateTime createdOn;
    private String createdBy;
    private LocalDateTime updatedOn;
    private Long updatedBy;
}

