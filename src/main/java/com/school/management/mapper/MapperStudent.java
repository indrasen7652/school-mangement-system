package com.school.management.mapper;

import com.school.management.dto.UserDTO;
import com.school.management.dto.student.AddressDTO;
import com.school.management.dto.student.StudentDTO;
import com.school.management.entity.UserEntity;
import com.school.management.entity.student.AddressEntity;
import com.school.management.entity.student.StudentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperStudent {
    public static StudentEntity map(final StudentDTO input) {
        ModelMapper modelMapper = new ModelMapper();
        StudentEntity output  =
                modelMapper.map(input, StudentEntity.class);
        return output;
    }
    public static StudentDTO map(final StudentEntity input) {
        ModelMapper modelMapper = new ModelMapper();
        StudentDTO output  =
                modelMapper.map(input,StudentDTO.class);
        return output;
    }


    public static AddressDTO map(final AddressEntity input) {
        ModelMapper modelMapper = new ModelMapper();
        AddressDTO output  =
                modelMapper.map(input, AddressDTO.class);
        return output;
    }
    public static AddressEntity map(final AddressDTO input) {
        ModelMapper modelMapper = new ModelMapper();
        AddressEntity output  =
                modelMapper.map(input,AddressEntity.class);
        return output;
    }


    public static UserEntity map1(final UserDTO input) {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity output  =
                modelMapper.map(input, UserEntity.class);
        return output;
    }
    public static UserDTO map1(final UserEntity input) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO output  =
                modelMapper.map(input,UserDTO.class);
        return output;
    }

}
