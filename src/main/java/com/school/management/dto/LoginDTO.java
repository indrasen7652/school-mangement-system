package com.school.management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "DTO for user login credentials.")
public class LoginDTO {

    @NotBlank(message = "User ID is required")
    @Size(min = 4, max = 20, message = "User ID must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "User ID can only contain letters, numbers, and underscores")
    @Schema(description = "Username of the user", example = "john@7652")
    String username;


    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema(description = "Password of the user", example = "password123")
    String password;

    String role;

}
