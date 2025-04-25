package com.school.management.repository.teacher;

import com.school.management.entity.teacher.TeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherSubjectRepository extends JpaRepository<TeacherSubject ,Long> {

    @Query(nativeQuery = true, value = "Select * from teacher_subject where teacher_id=?1")
    List<TeacherSubject> findByTeacherID(Long uid);

}
