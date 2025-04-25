package com.school.management.constants;

public class MessageConstants {
    public static final String USER_NOT_FOUND = "User not found";
    public static final String ACCESS_DENIED = "You do not have permission to access this resource";
    public static final String LOGIN_SUCCESS = "Login successful.";
    public static final String USER_REGISTRATION_SUCCESS = "User registration completed successfully.";

    // USER
    public static final String USER_DEACTIVATED_EXPIRED = "User is either deactivated or the account has expired.";

    // HEADER
    public static final String AUTHORIZATION_HEADER_MISSING = "Authorization header is missing.";

    // Token API
    public static final String INVALID_TOKEN = "Invalid token provided.";
    public static final String INVALID_TOKEN_EXPIRED_TOKEN = "The token has expired. Please login again.";
    public static final String INVALID_TOKEN_PREFIX = "Invalid token prefix. Expected 'Bearer'.";
    public static final String TOKEN_PREFIX = "Bearer ";


    // Login Response

    public static final String USER_PASSWORD_INCORRECT ="Username or password is incorrect. Please try again.";
    public static final String USER_AUTHENTICATION_FAILED="Authentication failed. Please try again.";
    public static final String TOKEN_GENERATE_FAILED="Unable to generate JWT token due to internal error.";
}

