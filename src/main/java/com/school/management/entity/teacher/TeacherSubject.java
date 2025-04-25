package com.school.management.entity.teacher;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "teacher_subject")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeacherSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Long uid;

    @Column(name = "teacher_id")
    private Long teacherId;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "status")
    private Short status;

    @Column(name = "start_on")
    private LocalDate startOn;

    @Column(name = "end_on")
    private LocalDate endOn;
}
