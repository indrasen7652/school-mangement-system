package com.school.management.repository.student;

import com.school.management.entity.student.StudentEntity;
import com.school.management.entity.teacher.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity,Long> {


    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "update students set status=?2, remarks=?3 where uid=?1")
    void updateStudentStatus(Long uid, Short status, String remarks);

    @Query(nativeQuery = true,value = "SELECT s.uid,s.registration_num,s.user_id,s.first_name,s.last_name,s.email,s.phone_number,s.gender,s.status,s.remarks,\n" +
            "s.created_on,s.created_by,s.updated_on,s.updated_by,a.uid,a.student_id,a.address_type,a.street,a.city,a.state,a.country,a.pincode \n" +
            ",a.status FROM public.students s left join public.address a on s.uid=a.student_id;")
    List<Object[]> getAllTeacherList();



    @Query(nativeQuery = true,value = "select * from students where email ILIKE ?1")
    StudentEntity checkUserEmailExist(String emailId);

    @Query(nativeQuery = true,value = "select * from students where phone_number=?1")
    StudentEntity checkUserMobileExist(String phoneNumber);

    @Query(nativeQuery = true,value = "select * from students where user_id ILIKE ?1")
    StudentEntity checkUserIdExist(String UserID);

    @Query(nativeQuery = true,value = "select * from students where user_id=?1")
    StudentEntity findByUserId(String userId);

    @Query(value = "SELECT COUNT(*) + 1 FROM students", nativeQuery = true)
    int getNextStudentRegNumber();

    @Query(value = "SELECT COUNT(*) FROM students WHERE DATE(created_on) = CURRENT_DATE", nativeQuery = true)
    Long toDayCreatedStudent();
}
