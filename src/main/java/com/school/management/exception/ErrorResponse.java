package com.school.management.exception;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {

    private Timestamp timestamp;
    private String message;
    private String details;

}

