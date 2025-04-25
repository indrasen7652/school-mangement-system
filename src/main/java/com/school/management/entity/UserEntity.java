package com.school.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "uid")
    Long uid;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "user_id", nullable = false)
    String userId;

    @Column(name = "password")
    String password;

    @Column(name = "status")
    Short status; // 1 Active and 2 DeActive

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "updated_by")
    private Long updatedBy;
}
