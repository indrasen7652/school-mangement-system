package com.school.management.repository.teacher;

import com.school.management.entity.teacher.TeacherEntity;
import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity,Long> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "update teachers set status=?2, remarks=?3 where uid=?1")
    void updateTeacherStatus(Long uid, Short status, String remarks);

    @Query(nativeQuery = true,value = "Select t.uid,t.registration_num,t.user_id,t.first_name,t.last_name,t.email,t.phone_number,t.gender,t.date_of_joining,t.status,t.created_on,\n" +
            "t.created_by,t.updated_on,t.updated_by,ts.uid,ts.teacher_id,ts.subject_name,ts.status,ts.start_on,ts.end_on,ta.uid,ta.teacher_id,\n" +
            "ta.address_type,ta.street,ta.city,ta.state,ta.country,ta.pincode,ta.status\n" +
            "From teachers t Left join teacher_subject ts On t.uid=ts.teacher_id Left join teacher_address ta On t.uid=ta.teacher_id;")
    List<Object[]> getAllTeacherList();


    @Query(nativeQuery = true,value = "select * from teachers where email ILIKE ?1")
    TeacherEntity checkUserEmailExist(String emailId);

    @Query(nativeQuery = true,value = "select * from teachers where phone_number=?1")
    TeacherEntity checkUserMobileExist(String phoneNumber);

    @Query(nativeQuery = true,value = "select * from teachers where user_id ILIKE ?1")
    TeacherEntity checkUserIdExist(String UserID);

    @Query(nativeQuery = true,value = "select * from teachers where user_id=?1")
    TeacherEntity findByUserId(String userId);

    @Query(value = "SELECT COUNT(*) + 1 FROM teachers", nativeQuery = true)
    int getNextTeacherRegNumber();

    @Query(value = "SELECT COUNT(*) FROM teachers WHERE DATE(created_on) = CURRENT_DATE", nativeQuery = true)
    Long toDayCreatedTeacher();
}
