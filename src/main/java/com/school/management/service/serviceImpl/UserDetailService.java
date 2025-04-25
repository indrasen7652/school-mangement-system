package com.school.management.service.serviceImpl;


import com.school.management.entity.UserEntity;;
import com.school.management.entity.student.StudentEntity;
import com.school.management.entity.teacher.TeacherEntity;
import com.school.management.repository.UserRepository;
import com.school.management.repository.student.StudentRepository;
import com.school.management.repository.teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("Role not provided. Use loadUserByUsernameAndRole instead.");
    }

    public UserDetails loadUserByUsernameAndRole(String username, String role) throws UsernameNotFoundException {
        switch (role.toUpperCase().replace("ROLE_", "")) {
            case "ADMIN":
                UserEntity user = userRepository.findByUserId(username);
                if (user != null) {
                    return User.builder()
                            .username(user.getUserId())
                            .password(user.getPassword())
                            .roles("ADMIN")
                            .disabled(user.getStatus() != 1)
                            .build();
                } else {
                    throw new UsernameNotFoundException("User not found with username: " + username);
                }
            case "TEACHER":
                TeacherEntity teacher = teacherRepository.findByUserId(username);
                if (teacher != null) {
                    return User.builder()
                            .username(teacher.getUserId())
                            .password(teacher.getPassword())
                            .roles("TEACHER")
                            .disabled(teacher.getStatus() != 1)
                            .build();
                } else {
                    throw new UsernameNotFoundException("Teacher not found with username: " + username);
                }
            case "STUDENT":
                StudentEntity student = studentRepository.findByUserId(username);
                if (student != null) {
                    return User.builder()
                            .username(student.getUserId())
                            .password(student.getPassword())
                            .roles("STUDENT")
                            .disabled(student.getStatus() != 1)
                            .build();
                } else {
                    throw new UsernameNotFoundException("Student not found with username: " + username);
                }
            default:
                throw new UsernameNotFoundException("Invalid role: " + role);
        }
    }
}
