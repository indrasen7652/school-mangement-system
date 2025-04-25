package com.school.management.mapper;

import com.school.management.dto.teacher.TeacherAddressDTO;
import com.school.management.dto.teacher.TeacherDTO;
import com.school.management.dto.teacher.TeacherSubjectDTO;
import com.school.management.entity.teacher.TeacherAddress;
import com.school.management.entity.teacher.TeacherEntity;
import com.school.management.entity.teacher.TeacherSubject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class MapperTeacher {


    public static TeacherAddressDTO map(final TeacherAddress input) {
        ModelMapper modelMapper = new ModelMapper();
        TeacherAddressDTO output  =
                modelMapper.map(input, TeacherAddressDTO.class);
        return output;
    }
    public static TeacherAddress map(final TeacherAddressDTO input) {
        ModelMapper modelMapper = new ModelMapper();
        TeacherAddress output  =
                modelMapper.map(input,TeacherAddress.class);
        return output;
    }


    public static TeacherEntity map(final TeacherDTO input) {
        ModelMapper modelMapper = new ModelMapper();
        TeacherEntity output  =
                modelMapper.map(input, TeacherEntity.class);
        return output;
    }
    public static TeacherDTO map(final TeacherEntity input) {
        ModelMapper modelMapper = new ModelMapper();
        TeacherDTO output  =
                modelMapper.map(input,TeacherDTO.class);
        return output;
    }


    public static TeacherSubjectDTO map(final TeacherSubject input) {
        ModelMapper modelMapper = new ModelMapper();
        TeacherSubjectDTO output  =
                modelMapper.map(input, TeacherSubjectDTO.class);
        return output;
    }
    public static TeacherSubject map(final TeacherSubjectDTO input) {
        ModelMapper modelMapper = new ModelMapper();
        TeacherSubject output  =
                modelMapper.map(input,TeacherSubject.class);
        return output;
    }



}
