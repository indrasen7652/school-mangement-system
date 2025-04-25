package com.school.management.utils;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RegistrationNumber {
    public static String generateRegistrationNumber(String role, int nextNumber) {
        String prefix = "REG";
        String year = String.valueOf(LocalDate.now().getYear());
        String roleCode;
        switch (role.toUpperCase()) {
            case "STUDENT":
                roleCode = "STU";
                break;
            case "TEACHER":
                roleCode = "TCH";
                break;
            default:
                roleCode = "GEN";
        }
        String formattedNumber = String.format("%04d", nextNumber);
        return prefix + "-" + roleCode + "-" + year + "-" + formattedNumber;
    }

}

