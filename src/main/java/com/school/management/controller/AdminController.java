package com.school.management.controller;

import com.school.management.constants.UrlConstants;
import com.school.management.dto.ResponseData;
import com.school.management.dto.student.StudentDTO;
import com.school.management.dto.teacher.TeacherDTO;
import com.school.management.entity.student.StudentEntity;
import com.school.management.entity.teacher.TeacherEntity;
import com.school.management.exception.ValidationException;
import com.school.management.repository.student.StudentRepository;
import com.school.management.repository.teacher.TeacherRepository;
import com.school.management.service.StudentService;
import com.school.management.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(UrlConstants.ADMIN_BASE_URL)
public class AdminController {


    @Autowired
    TeacherService teacherService;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    @PostMapping(UrlConstants.VERSION_ONE+"teacher/registration")
    public ResponseEntity<?> createTeacher(@Valid @RequestBody TeacherDTO teacherDTO){
        TeacherEntity teacherEntity =teacherRepository.checkUserMobileExist(teacherDTO.getPhoneNumber());
        if (teacherEntity!=null){
            throw new ValidationException("User phone number Already exist");
        }
        TeacherEntity teacherEntity1=teacherRepository.checkUserEmailExist(teacherDTO.getEmail());
        if (teacherEntity1!=null){
            throw new ValidationException("User email Already exist");
        }
        TeacherEntity teacherEntity2=teacherRepository.checkUserIdExist(teacherDTO.getUserId());
        if (teacherEntity2!=null){
            throw new ValidationException("User id Already exist");
        }
        ResponseData responseData=teacherService.teacherSave(teacherDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @PutMapping(UrlConstants.VERSION_ONE+"teacher/update")
    public ResponseEntity<?> updatedTeacher(@RequestParam(name = "uid") Long uid,@Valid @RequestBody TeacherDTO teacherDTO){
        ResponseData responseData=new ResponseData();
        responseData.setMessage("Updation");
        responseData.setSubMessage("Teacher updation successfully done");
        responseData.setData(teacherService.teacherUpdate(uid,teacherDTO));
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @PutMapping(UrlConstants.VERSION_ONE+"teacher/status/update")
    public ResponseEntity<?> activateAndDeactivateTeacher(
            @RequestParam(value = "uid") Long uid,
            @RequestParam(value = "status") Short status,
            @RequestParam(value = "remark",required = false) String remark
    ){
        ResponseData responseData=teacherService.updateTeacherStatus(uid,status,remark);
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @GetMapping(UrlConstants.VERSION_ONE+"teachers")
    public ResponseEntity<ResponseData> getAllTeacher(){
        ResponseData responseData=new ResponseData();
        responseData.setMessage("Details");
        responseData.setSubMessage("Get all Teacher Details successfully");
        responseData.setData(teacherService.getAllTeacherList());
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @GetMapping(UrlConstants.VERSION_ONE+"teacher")
    public ResponseEntity<ResponseData> getAllTeacherByID(@RequestParam(name="teacherId") Long teacherId){
        ResponseData responseData=new ResponseData();
        responseData.setMessage("Details");
        responseData.setSubMessage("Get Details by Teacher uid");
        responseData.setData(teacherService.getTeacherByUid(teacherId));
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @PostMapping(UrlConstants.VERSION_ONE+"student/registration")
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentDTO studentDTO){

        StudentEntity studentEntity =studentRepository.checkUserMobileExist(studentDTO.getPhoneNumber());
        if (studentEntity!=null){
            throw new ValidationException("User phone number Already exist");
        }
        StudentEntity studentEntity1=studentRepository.checkUserEmailExist(studentDTO.getEmail());
        if (studentEntity1!=null){
            throw new ValidationException("User email Already exist");
        }
        StudentEntity studentEntity2=studentRepository.checkUserIdExist(studentDTO.getUserId());
        if (studentEntity2!=null){
            throw new ValidationException("User id Already exist");
        }

        ResponseData responseData=studentService.studentSave(studentDTO);
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


}
