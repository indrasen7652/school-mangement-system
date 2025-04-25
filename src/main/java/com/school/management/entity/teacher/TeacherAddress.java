package com.school.management.entity.teacher;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "teacher_address")
@AllArgsConstructor
@NoArgsConstructor
public class TeacherAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "uid")
    private Long uid;

    @Column(name = "teacher_id", nullable = false)
    private Long teacherId;

    @Column(name = "address_type")
    private Short addressType;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "country")
    private String country;

    @Column(name = "status")
    private Short status;
}
