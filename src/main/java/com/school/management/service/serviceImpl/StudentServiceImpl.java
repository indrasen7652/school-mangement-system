package com.school.management.service.serviceImpl;

import com.school.management.dto.ChangePasswordDTO;
import com.school.management.dto.ResponseData;
import com.school.management.dto.student.AddressDTO;
import com.school.management.dto.student.StudentDTO;
import com.school.management.entity.UserEntity;
import com.school.management.entity.student.AddressEntity;
import com.school.management.entity.student.StudentEntity;

import com.school.management.exception.InternalException;
import com.school.management.exception.ResourceNotFoundException;
import com.school.management.mapper.MapperStudent;

import com.school.management.repository.student.StudentAddressRepository;
import com.school.management.repository.student.StudentRepository;
import com.school.management.service.StudentService;
import com.school.management.utils.RegistrationNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentAddressRepository studentAddressRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    MapperStudent mapperStudent;

    @Override
    public ResponseData studentSave(StudentDTO studentDTO) {
        ResponseData responseData = new ResponseData();
        StudentDTO studentDTO1;
        StudentEntity studentEntity=MapperStudent.map(studentDTO);
        studentEntity.setRegistrationNumber(RegistrationNumber.generateRegistrationNumber("STUDENT",studentRepository.getNextStudentRegNumber()));
        studentEntity.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
        studentEntity.setCreatedOn(LocalDateTime.now());
        studentDTO1 = MapperStudent.map(studentRepository.save(studentEntity));
        if (studentDTO.getAddressDTOList() != null) {
            studentDTO.getAddressDTOList().forEach(addressDTO -> addressDTO.setStudentUid(studentDTO1.getUid()));
            List<AddressEntity> teacherAddressesList = studentDTO.getAddressDTOList()
                    .stream()
                    .map(MapperStudent::map)
                    .collect(Collectors.toList());
            studentDTO1.setAddressDTOList(studentAddressRepository.saveAll(teacherAddressesList).stream()
                    .map(MapperStudent::map)
                    .collect(Collectors.toList()));
        }
        studentDTO.setPassword(null);
        responseData.setMessage("Registration");
        responseData.setSubMessage("Student Registration successfully done");
        responseData.setData(studentDTO1);
        return responseData;
    }

    @Override
    public ResponseData studentUpdate(Long uid, StudentDTO studentDTO) {
        ResponseData responseData = new ResponseData();
        StudentDTO studentDTO1;
        StudentEntity existingStudent = studentRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        existingStudent.setFirstName(studentDTO.getFirstName());
        existingStudent.setLastName(studentDTO.getLastName());
        existingStudent.setEmail(studentDTO.getEmail());
        existingStudent.setPhoneNumber(studentDTO.getPhoneNumber());
        existingStudent.setGender(studentDTO.getGender());
        existingStudent.setStatus(studentDTO.getStatus());
        existingStudent.setUpdatedOn(LocalDateTime.now());
        existingStudent.setUpdatedBy(studentDTO.getUpdatedBy());
        studentDTO1=MapperStudent.map(studentRepository.save(existingStudent));
        studentDTO.setPassword(null);
        if (studentDTO.getAddressDTOList() != null) {
            studentDTO.getAddressDTOList().forEach(addressDTO -> addressDTO.setStudentUid(studentDTO1.getUid()));
            List<AddressEntity> teacherAddressesList = studentDTO.getAddressDTOList()
                    .stream()
                    .map(MapperStudent::map)
                    .collect(Collectors.toList());
            studentDTO1.setAddressDTOList(studentAddressRepository.saveAll(teacherAddressesList).stream()
                    .map(MapperStudent::map)
                    .collect(Collectors.toList()));
        }
        responseData.setMessage("Updated");
        responseData.setSubMessage("Student Updation successfully done");
        responseData.setData(studentDTO1);
        return responseData;
    }

    @Override
    public List<StudentDTO> getAllStudentList() {
        Map<Integer, StudentDTO> infoDTOMap = new HashMap<>();
        List<Object[]> results = studentRepository.getAllTeacherList();
        for (Object[] result : results) {
            Integer infoId = ((Long) result[0]).intValue();
            StudentDTO dto = infoDTOMap.computeIfAbsent(infoId, id -> {
                StudentDTO studentDTO = new StudentDTO();
                studentDTO.setUid(Long.valueOf(id));
                studentDTO.setRegistrationNumber((String) result[1]);
                studentDTO.setUserId((String) result[2]);
                studentDTO.setFirstName((String) result[3]);
                studentDTO.setLastName((String) result[4]);
                studentDTO.setEmail((String) result[4]);
                studentDTO.setPhoneNumber((String) result[6]);
                studentDTO.setGender((String) result[7]);
                studentDTO.setStatus((Short) result[8]);
                studentDTO.setRemarks((String) result[9]);
                studentDTO.setCreatedOn(((Timestamp) result[10]).toLocalDateTime());
                studentDTO.setCreatedBy(((Long) result[11]));
                studentDTO.setUpdatedOn(((Timestamp) result[12]).toLocalDateTime());
                studentDTO.setUpdatedBy((Long) result[13]);
                studentDTO.setAddressDTOList(new ArrayList<>());
                return studentDTO;
            });
            if (result[14] != null) {
                AddressDTO addressDTO= new AddressDTO();
                addressDTO.setUid((Long) result[14]);
                addressDTO.setStudentUid((Long) result[15]);
                addressDTO.setAddressType((Short) result[16]);
                addressDTO.setStreet((String) result[17]);
                addressDTO.setCity((String) result[18]);
                addressDTO.setState((String) result[19]);
                addressDTO.setCountry((String) result[20]);
                addressDTO.setPincode((String) result[21]);
                addressDTO.setStatus((Short) result[22]);
                dto.getAddressDTOList().add(addressDTO);
            }
        }
        return new ArrayList<>(infoDTOMap.values());
    }

    @Override
    public StudentDTO getStudentByUid(Long uid) {
        StudentDTO studentDTO= MapperStudent.map(studentRepository.findById(uid).orElseThrow(()-> new UsernameNotFoundException("Student Not Found Exception")));
        studentDTO.setPassword(null);
        studentDTO.setAddressDTOList(studentAddressRepository.findByStudentID(uid).stream().map(MapperStudent::map).collect(Collectors.toList()));
        return studentDTO;
    }

    @Override
    public ResponseData updateStudentStatus(Long uid, Short status, String remarks) {
        ResponseData responseData=new ResponseData();
        try {
            StudentEntity result = studentRepository.findById(uid).orElseThrow(
                    () -> new ResourceNotFoundException("Student not found with id: " + uid)
            );
            studentRepository.updateStudentStatus(uid,status,remarks);
            if(status==2){
                responseData.setMessage("Deactivated");
                responseData.setSubMessage("Student '"+result.getFirstName()+" "+result.getLastName()+"' has been deactivated");
            }
            if(status==1){
                responseData.setMessage("Activated");
                responseData.setSubMessage("Student '"+result.getFirstName()+" "+result.getLastName()+"' has been activated");
            }
        }catch (Exception e){
            throw new InternalException("Teacher activated/deactivated failed");
        }
        return responseData;
    }

    @Override
    public String changePassword(ChangePasswordDTO dto) {
        StudentEntity user = studentRepository.findByUserId(dto.getUserId());
        if (user==null){
            throw new ResourceNotFoundException("User not founds");
        }
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setUpdatedOn(LocalDateTime.now());
        studentRepository.save(user);
        return "Password changed successfully";
    }
}
