package com.school.management.service.serviceImpl;

import com.school.management.dto.ChangePasswordDTO;
import com.school.management.dto.ResponseData;
import com.school.management.dto.teacher.TeacherAddressDTO;
import com.school.management.dto.teacher.TeacherDTO;
import com.school.management.dto.teacher.TeacherSubjectDTO;
import com.school.management.entity.student.StudentEntity;
import com.school.management.entity.teacher.TeacherAddress;
import com.school.management.entity.teacher.TeacherEntity;
import com.school.management.entity.teacher.TeacherSubject;
import com.school.management.exception.InternalException;
import com.school.management.exception.ResourceNotFoundException;
import com.school.management.mapper.MapperTeacher;
import com.school.management.repository.teacher.TeacherAddressRepository;
import com.school.management.repository.teacher.TeacherRepository;
import com.school.management.repository.teacher.TeacherSubjectRepository;
import com.school.management.service.TeacherService;
import com.school.management.utils.RegistrationNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherRepository teacherServiceRepository;

    @Autowired
    TeacherAddressRepository teacherAddressRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    TeacherSubjectRepository teacherSubjectRepository;

    @Autowired
    MapperTeacher mapper;

    @Override
    public ResponseData teacherSave(TeacherDTO teacherDTO) {
        ResponseData responseData = new ResponseData();
        TeacherDTO teacherDTO1;
        TeacherEntity teacherEntity=MapperTeacher.map(teacherDTO);
        teacherEntity.setRegistrationNumber(RegistrationNumber.generateRegistrationNumber("TEACHER",teacherServiceRepository.getNextTeacherRegNumber()));
        teacherEntity.setPassword(passwordEncoder.encode(teacherDTO.getPassword()));
        teacherEntity.setCreatedOn(LocalDateTime.now());
        teacherDTO1 = MapperTeacher.map(teacherServiceRepository.save(teacherEntity));
        if (teacherDTO.getTeacherAddressDTOList() != null) {
            teacherDTO.getTeacherAddressDTOList().forEach(addressDTO -> addressDTO.setTeacherId(teacherDTO1.getUid()));
            List<TeacherAddress> teacherAddressesList = teacherDTO.getTeacherAddressDTOList()
                    .stream()
                    .map(MapperTeacher::map)
                    .collect(Collectors.toList());
            teacherDTO1.setTeacherAddressDTOList(teacherAddressRepository.saveAll(teacherAddressesList).stream()
                    .map(MapperTeacher::map)
                    .collect(Collectors.toList()));
        }
        if (teacherDTO.getTeacherSubjectDTOList() != null) {
            teacherDTO.getTeacherSubjectDTOList().forEach(addressDTO -> addressDTO.setTeacherId(teacherDTO1.getUid()));
            List<TeacherSubject> teacherAddressesList = teacherDTO.getTeacherSubjectDTOList()
                    .stream()
                    .map(MapperTeacher::map)
                    .collect(Collectors.toList());
            teacherDTO1.setTeacherSubjectDTOList(teacherSubjectRepository.saveAll(teacherAddressesList).stream()
                    .map(MapperTeacher::map)
                    .collect(Collectors.toList()));
        }
        teacherDTO1.setPassword(null);
        responseData.setMessage("Registration");
        responseData.setSubMessage("Teacher Registration successfully done");
        responseData.setData(teacherDTO1);
        return responseData;
    }

    @Override
    public TeacherDTO teacherUpdate(Long uid, TeacherDTO teacherDTO) {
        TeacherEntity existingTeacher = teacherServiceRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        existingTeacher.setFirstName(teacherDTO.getFirstName());
        existingTeacher.setLastName(teacherDTO.getLastName());
        existingTeacher.setEmail(teacherDTO.getEmail());
        existingTeacher.setPhoneNumber(teacherDTO.getPhoneNumber());
        existingTeacher.setGender(teacherDTO.getGender());
        existingTeacher.setDateOfJoining(teacherDTO.getDateOfJoining());
        existingTeacher.setStatus(teacherDTO.getStatus());
        existingTeacher.setUpdatedOn(LocalDateTime.now());
        existingTeacher.setUpdatedBy(teacherDTO.getUpdatedBy());
        TeacherEntity updatedTeacher = teacherServiceRepository.save(existingTeacher);

        if (teacherDTO.getTeacherAddressDTOList() != null) {
            teacherDTO.getTeacherAddressDTOList().forEach(addressDTO -> addressDTO.setTeacherId(updatedTeacher.getUid()));
            List<TeacherAddress> updatedTeacherAddresses = teacherDTO.getTeacherAddressDTOList()
                    .stream()
                    .map(MapperTeacher::map)
                    .collect(Collectors.toList());
            teacherAddressRepository.saveAll(updatedTeacherAddresses);
        }
        if (teacherDTO.getTeacherSubjectDTOList() != null) {
            teacherDTO.getTeacherSubjectDTOList().forEach(subjectDTO -> subjectDTO.setTeacherId(updatedTeacher.getUid()));
            List<TeacherSubject> updatedTeacherSubjects = teacherDTO.getTeacherSubjectDTOList()
                    .stream()
                    .map(MapperTeacher::map)
                    .collect(Collectors.toList());

            teacherSubjectRepository.saveAll(updatedTeacherSubjects);
        }
        TeacherDTO updatedTeacherDTO = MapperTeacher.map(updatedTeacher);
        updatedTeacherDTO.setPassword(null);
        return updatedTeacherDTO;
    }

    @Override
    public List<TeacherDTO> getAllTeacherList() {
        Map<Integer, TeacherDTO> infoDTOMap = new HashMap<>();
        List<Object[]> results = teacherServiceRepository.getAllTeacherList();
        for (Object[] result : results) {
            Integer infoId = ((Long) result[0]).intValue();
            TeacherDTO dto = infoDTOMap.computeIfAbsent(infoId, id -> {
                TeacherDTO teacherDTO = new TeacherDTO();
                teacherDTO.setUid(Long.valueOf(id));
                teacherDTO.setRegistrationNumber((String) result[1]);
                teacherDTO.setUserId((String) result[2]);
                teacherDTO.setFirstName((String) result[3]);
                teacherDTO.setLastName((String) result[4]);
                teacherDTO.setEmail((String) result[4]);
                teacherDTO.setPhoneNumber((String) result[6]);
                teacherDTO.setGender((String) result[7]);
                teacherDTO.setDateOfJoining(result[8] != null ? ((Date) result[8]).toLocalDate() : null);
                teacherDTO.setStatus((Short) result[9]);
                teacherDTO.setCreatedOn(((Timestamp) result[10]).toLocalDateTime());
                teacherDTO.setCreatedBy((Long) result[11]);
                teacherDTO.setUpdatedOn(((Timestamp) result[12]).toLocalDateTime());
                teacherDTO.setUpdatedBy((Long) result[13]);
                teacherDTO.setTeacherAddressDTOList(new ArrayList<>());
                teacherDTO.setTeacherSubjectDTOList(new ArrayList<>());
                return teacherDTO;
            });
            if (result[14] != null) {
                TeacherSubjectDTO teacherSubjectDTO = new TeacherSubjectDTO();
                teacherSubjectDTO.setUid((Long) result[14]);
                teacherSubjectDTO.setTeacherId((Long) result[15]);
                teacherSubjectDTO.setSubjectName((String) result[16]);
                teacherSubjectDTO.setStatus((Short) result[17]);
                teacherSubjectDTO.setStartOn(result[18] != null ? ((Date) result[18]).toLocalDate() : null);
                teacherSubjectDTO.setEndOn(result[19] != null ? ((Date) result[19]).toLocalDate() : null);
                dto.getTeacherSubjectDTOList().add(teacherSubjectDTO);
            }
            if (result[20] != null) {
                TeacherAddressDTO teacherAddressDTO= new TeacherAddressDTO();
                teacherAddressDTO.setUid((Long) result[20]);
                teacherAddressDTO.setTeacherId((Long) result[21]);
                teacherAddressDTO.setAddressType((Short) result[22]);
                teacherAddressDTO.setStreet((String) result[23]);
                teacherAddressDTO.setCity((String) result[24]);
                teacherAddressDTO.setState((String) result[25]);
                teacherAddressDTO.setCountry((String) result[26]);
                teacherAddressDTO.setPincode((String) result[27]);
                teacherAddressDTO.setStatus((Short) result[28]);
                dto.getTeacherAddressDTOList().add(teacherAddressDTO);
            }
        }
        return new ArrayList<>(infoDTOMap.values());
    }

    @Override
    public TeacherDTO getTeacherByUid(Long uid) {
            TeacherDTO teacherDTO=MapperTeacher.map(teacherServiceRepository.findById(uid).orElseThrow(()-> new UsernameNotFoundException("Teacher Not Found Exception")));
            teacherDTO.setPassword(null);
            teacherDTO.setTeacherSubjectDTOList(teacherSubjectRepository.findByTeacherID(uid).stream().map(MapperTeacher::map).collect(Collectors.toList()));
            teacherDTO.setTeacherAddressDTOList(teacherAddressRepository.findByTeacherID(uid).stream().map(MapperTeacher::map).collect(Collectors.toList()));
        return teacherDTO;
    }

    @Override
    public ResponseData updateTeacherStatus(Long uid, Short status, String remarks) {
        ResponseData responseData=new ResponseData();
            try {
                TeacherEntity result = teacherServiceRepository.findById(uid).orElseThrow(
                        () -> new ResourceNotFoundException("User not found with id: " + uid)
                );
                teacherServiceRepository.updateTeacherStatus(uid,status,remarks);
                if(status==2){
                    responseData.setMessage("Deactivated");
                    responseData.setSubMessage("Teacher '"+result.getFirstName()+" "+result.getLastName()+"' has been deactivated");
                }
                if(status==1){
                    responseData.setMessage("Activated");
                    responseData.setSubMessage("Teacher '"+result.getFirstName()+" "+result.getLastName()+"' has been activated");
                }
            }catch (Exception e){
                throw new InternalException("Teacher activated/deactivated failed");
            }
            return responseData;
    }

    @Override
    public String changePassword(ChangePasswordDTO dto) {
        TeacherEntity user = teacherServiceRepository.findByUserId(dto.getUserId());
        if (user==null){
            throw new ResourceNotFoundException("User not founds");
        }
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setUpdatedOn(LocalDateTime.now());
        teacherServiceRepository.save(user);
        return "Password changed successfully";
    }
}
