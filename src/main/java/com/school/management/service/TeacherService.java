package com.school.management.service;

import com.school.management.dto.ChangePasswordDTO;
import com.school.management.dto.ResponseData;
import com.school.management.dto.teacher.TeacherDTO;

import java.util.List;

public interface TeacherService {
    ResponseData teacherSave(TeacherDTO teacherDTO);
    TeacherDTO teacherUpdate(Long uid,TeacherDTO teacherDTO);
    List<TeacherDTO> getAllTeacherList();
    TeacherDTO getTeacherByUid(Long uid);
    ResponseData updateTeacherStatus(Long uid, Short status, String remarks);

    String changePassword(ChangePasswordDTO dto);

}
