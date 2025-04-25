package com.school.management.service.serviceImpl;

import com.school.management.dto.ResponseData;
import com.school.management.dto.UserDTO;
import com.school.management.entity.UserEntity;
import com.school.management.mapper.MapperStudent;
import com.school.management.repository.UserRepository;
import com.school.management.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    MapperStudent  studentMapper;

    public ResponseData createOrUpdateUser(UserDTO userDTO) {
        System.out.println(userDTO.getPassword());
        System.out.println(userDTO.getUserId());
        ResponseData responseData=new ResponseData();
        if (userDTO.getUid() == null) {
            UserEntity userEntity=MapperStudent.map1(userDTO);
            userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userEntity.setCreatedOn(LocalDateTime.now());
            responseData.setMessage("Created");
            responseData.setSubMessage("Create Admin users");
            responseData.setData(MapperStudent.map1(userRepository.save(userEntity)));
        } else {
            UserEntity userEntity =userRepository.findById(userDTO.getUid()).orElseThrow(()-> new UsernameNotFoundException("Admin Not found"));
            userEntity.setName(userDTO.getName());
            userEntity.setUpdatedOn(LocalDateTime.now());
            responseData.setMessage("Updated");
            responseData.setSubMessage("Updating Admin users");
            responseData.setData(MapperStudent.map1(userRepository.save(userEntity)));
        }
        return responseData;
    }


}
