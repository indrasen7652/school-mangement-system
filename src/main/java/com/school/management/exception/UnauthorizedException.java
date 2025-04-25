package com.school.management.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class UnauthorizedException {
    private String txnId;
    private String function;
    private Integer status;
    private String message;
    private HttpStatus httpStatus;
}
