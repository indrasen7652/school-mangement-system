package com.school.management.repository.teacher;

import com.school.management.entity.teacher.TeacherAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherAddressRepository extends JpaRepository<TeacherAddress,Long> {

    @Query(nativeQuery = true, value = "Select * from teacher_address where teacher_id=?1")
    List<TeacherAddress> findByTeacherID(Long uid);

}
