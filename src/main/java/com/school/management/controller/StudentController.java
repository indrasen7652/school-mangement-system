package com.school.management.controller;


import com.school.management.constants.UrlConstants;
import com.school.management.dto.ChangePasswordDTO;
import com.school.management.dto.ResponseData;
import com.school.management.dto.student.StudentDTO;
import com.school.management.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping(UrlConstants.STUDENT_BASE_URL)
public class StudentController {

    @Autowired
    StudentService studentService;

    @PutMapping(UrlConstants.VERSION_ONE+"student/update")
    public ResponseEntity<?> updatedStudent(@RequestParam(name = "uid") Long uid,@Valid @RequestBody StudentDTO studentDTO) {
        ResponseData responseData=studentService.studentUpdate(uid,studentDTO);
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
            String result = studentService.changePassword(dto);
            return ResponseEntity.ok(Collections.singletonMap("message", result));
        }


}
