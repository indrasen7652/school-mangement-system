package com.school.management.constants;

public class UrlConstants {
    //,"/api/admin/**","/api/student/**","/api/teacher/**"
    public static final String[] PERMIT_URLS = {"/api/auth/**","/swagger-ui/**","/v3/api-docs/**"};
    public static final String[] ADMIN_URLS = {"/api/admin/**"};
    public static final String[] STUDENT_URLS = {"/api/student/**"};
    public static final String[] TEACHER_URLS = {"/api/teacher/**"};

    public static final String VERSION_ONE="v1/";
    public static final String AUTH_BASE_URL="/api/auth/";
    public static final String ADMIN_BASE_URL="/api/admin/";
    public static final String TEACHER_BASE_URL="/api/teacher/";
    public static final String STUDENT_BASE_URL="/api/student/";
}
