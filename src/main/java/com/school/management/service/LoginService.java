package com.school.management.service;

import com.school.management.dto.LoginDTO;

import java.util.Map;

public interface LoginService {
    Map<String,String> loginAdmin(LoginDTO loginDTO);
}
