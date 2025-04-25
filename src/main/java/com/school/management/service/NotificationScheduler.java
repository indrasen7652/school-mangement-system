package com.school.management.service;

import com.school.management.repository.student.StudentRepository;
import com.school.management.repository.teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationScheduler {

    @Value("#{'${notification.to.email}'.split(',')}")
    private List<String> toEmails;

    @Value("#{'${notification.cc.email}'.split(',')}")
    private List<String> ccEmails;

    @Autowired
    EmailServiec emailServiec;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    StudentRepository studentRepository;

    @Scheduled(cron = "0 0/2 * * * *", zone = "Asia/Kolkata")
    public void notificationSendRecordUserToday() {
        try {
            Long todayTeacherRegistration = teacherRepository.toDayCreatedTeacher();
            Long todayStudentRegistration = studentRepository.toDayCreatedStudent();

            String subject = "Daily Registration Report - " + LocalDate.now();
            String body = String.format(
                    "Hello,\n\nHere is the registration summary for today (%s):\n\n" +
                            "- Teachers Registered: %d\n" +
                            "- Students Registered: %d\n\n" +
                            "Regards,\nSchool Management System",
                    LocalDate.now(), todayTeacherRegistration, todayStudentRegistration
            );
            emailServiec.sendEmailThroughGmailSSL(toEmails, ccEmails, subject, body, "text/Plain");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
