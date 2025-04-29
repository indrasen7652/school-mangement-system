package com.school.management.service.serviceImpl;

import com.school.management.dto.DepartmentDTO;
import com.school.management.entity.DepartmentEntity;
import com.school.management.exception.ResourceNotFoundException;
import com.school.management.mapper.MapperTeacher;
import com.school.management.repository.DepartmentRepository;
import com.school.management.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    MapperTeacher mapperTeacher;

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public DepartmentDTO registration(DepartmentDTO departmentDTO) {
        DepartmentEntity departmentEntity=MapperTeacher.map(departmentDTO);
        departmentEntity.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
        return MapperTeacher.map(departmentRepository.save(departmentEntity));
    }

    @Override
    public DepartmentDTO getDepartmentById(Long departmentId) {
        DepartmentEntity departmentEntity=departmentRepository.findById(departmentId).orElseThrow(()-> new ResourceNotFoundException("Department not found by this Id"));
        return MapperTeacher.map(departmentEntity);
    }
}
