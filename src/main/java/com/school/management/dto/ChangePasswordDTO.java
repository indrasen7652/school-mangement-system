package com.school.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
    private String userId;
}

