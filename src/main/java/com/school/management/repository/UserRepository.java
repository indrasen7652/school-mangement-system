package com.school.management.repository;


import com.school.management.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    @Query(nativeQuery = true,value = "Select * From users where user_id=:username")
    UserEntity findByUserId(@Param("username") String username);
}
