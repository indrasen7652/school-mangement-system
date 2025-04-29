package com.school.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    Long uid;

    @Column(name = "name")
    String name;

    @Column(name = "created_by")
    Long createdBy;

    @Column(name = "created_on")
    Timestamp createdOn;

    @Column(name = "updated_by")
    Long updatedBy;

    @Column(name = "updated_on")
    Timestamp updatedOn;

}
