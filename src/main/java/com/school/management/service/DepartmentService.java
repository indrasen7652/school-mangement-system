package com.school.management.service;

import com.school.management.dto.DepartmentDTO;

public interface DepartmentService {
    DepartmentDTO registration(DepartmentDTO departmentDTO);

    DepartmentDTO getDepartmentById(Long departmentId);
}
