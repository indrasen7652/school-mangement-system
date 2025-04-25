package com.school.management.controller;

import com.school.management.constants.UrlConstants;
import com.school.management.dto.ChangePasswordDTO;
import com.school.management.dto.ResponseData;
import com.school.management.dto.student.StudentDTO;
import com.school.management.dto.teacher.TeacherDTO;
import com.school.management.service.StudentService;
import com.school.management.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping(UrlConstants.TEACHER_BASE_URL)
public class TeacherController {

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @PutMapping(UrlConstants.VERSION_ONE+"teacher/update")
    public ResponseEntity<?> updatedTeacher(@RequestParam(name = "uid") Long uid, @Valid @RequestBody  TeacherDTO teacherDTO){
        ResponseData responseData=new ResponseData();
        responseData.setMessage("Updation");
        responseData.setSubMessage("Teacher updation successfully done");
        responseData.setData(teacherService.teacherUpdate(uid,teacherDTO));
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @GetMapping(UrlConstants.VERSION_ONE+"teacher")
    public ResponseEntity<ResponseData> getAllTeacherByID(@RequestParam(name="teacherId") Long teacherId){
        ResponseData responseData=new ResponseData();
        responseData.setMessage("Details");
        responseData.setSubMessage("Get Teacher details by Uid");
        responseData.setData(teacherService.getTeacherByUid(teacherId));
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @PutMapping(UrlConstants.VERSION_ONE+"student/update")
    public ResponseEntity<?> updatedStudent(@RequestParam(name = "uid") Long uid,@Valid @RequestBody StudentDTO studentDTO) {
        ResponseData responseData=studentService.studentUpdate(uid,studentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @PutMapping(UrlConstants.VERSION_ONE+"student/status/update")
    public ResponseEntity<?> activateAndDeactivateStudent(
            @RequestParam(value = "uid") Long uid,
            @RequestParam(value = "status") Short status,
            @RequestParam(value = "remark",required = false) String remark
    ){
        ResponseData responseData=studentService.updateStudentStatus(uid,status,remark);
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @GetMapping(UrlConstants.VERSION_ONE+"students")
    public ResponseEntity<ResponseData> getAllStudent(){
        ResponseData responseData=new ResponseData();
        responseData.setMessage("Details");
        responseData.setSubMessage("Get All Details of student");
        responseData.setData(studentService.getAllStudentList());
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @GetMapping(UrlConstants.VERSION_ONE+"student")
    public ResponseEntity<ResponseData> getAllStudentByID(@RequestParam(name="studentId") Long studentId){
        ResponseData responseData=new ResponseData();
        responseData.setMessage("Details");
        responseData.setSubMessage("Get Details by student uid");
        responseData.setData(studentService.getStudentByUid(studentId));
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @PostMapping(UrlConstants.VERSION_ONE+"change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO dto) {
        String result = teacherService.changePassword(dto);
        return ResponseEntity.ok(Collections.singletonMap("message", result));
    }
}
