package com.school.management.service;

import com.school.management.dto.ChangePasswordDTO;
import com.school.management.dto.ResponseData;
import com.school.management.dto.student.StudentDTO;
import com.school.management.entity.student.StudentEntity;

import java.util.List;

public interface StudentService {

    ResponseData studentSave(StudentDTO studentDTO);
    ResponseData studentUpdate(Long uid,StudentDTO studentDTO);
    List<StudentDTO> getAllStudentList();
    StudentDTO getStudentByUid(Long uid);
    ResponseData updateStudentStatus(Long uid, Short status, String remarks);

    String changePassword(ChangePasswordDTO dto);
}
