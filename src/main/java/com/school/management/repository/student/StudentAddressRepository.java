package com.school.management.repository.student;

import com.school.management.entity.student.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface StudentAddressRepository extends JpaRepository<AddressEntity,Long> {

    @Query(nativeQuery = true,value = "Select * from address where student_id=?1")
    List<AddressEntity> findByStudentID(Long uid);

}
