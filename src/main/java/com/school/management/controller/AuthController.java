package com.school.management.controller;


import com.school.management.constants.UrlConstants;
import com.school.management.dto.LoginDTO;
import com.school.management.dto.UserDTO;
import com.school.management.entity.UserEntity;
import com.school.management.entity.teacher.TeacherEntity;
import com.school.management.exception.ValidationException;
import com.school.management.kafka.ConsumerService;
import com.school.management.kafka.ProducerService;
import com.school.management.repository.UserRepository;
import com.school.management.service.LoginService;
import com.school.management.service.serviceImpl.AdminServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlConstants.AUTH_BASE_URL)
public class AuthController {

    @Autowired
    LoginService loginService;

    @Autowired
    ProducerService producerService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminServiceImpl adminService;

    @PostMapping(UrlConstants.VERSION_ONE+"login")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.status(HttpStatus.OK).body(loginService.loginAdmin(loginDTO));
    }



    @PostMapping(UrlConstants.VERSION_ONE+"admin")
    public ResponseEntity<?> AdminRegistration(@Valid @RequestBody UserDTO userDTO){
        UserEntity userEntity=userRepository.checkUserIdExist(userDTO.getUserId());
        if (userEntity!=null){
            throw new ValidationException("User id Already exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body(adminService.createOrUpdateUser(userDTO));
    }




    @PostMapping("/publish/{message}")
    public ResponseEntity<String> publishEvent(@PathVariable String message) {
        producerService.sendUserEvent(message);
        return ResponseEntity.ok("Message published to Kafka topic.");
    }

    @GetMapping("/publish/messages")
    public ResponseEntity<List<String>> getPublishedMessages() {
        return ResponseEntity.ok(producerService.getPublishedMessages());
    }
}
