package com.school.management.entity.student;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@ToString
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "uid")
    private Long uid;

    @Column(name = "student_id", nullable = false)
    private Long studentUid;

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
