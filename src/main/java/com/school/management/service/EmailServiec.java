package com.school.management.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
public class EmailServiec {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.host}")
    private String emailSMTPserver;

    @Value("${spring.mail.port}")
    private Integer emailSMTPPort;

    @Value("${spring.mail.sender}")
    private String senderEmailId;

    public void sendEmailThroughGmailSSL(List<String> receiverEmail,List<String> receiverCCEmail,String subject, String messageTest,String contentType) throws Exception {
        String htmlContent =messageTest;
        String senderEmail=senderEmailId;
        Properties props = new Properties();
        props.put("mail.smtp.host", emailSMTPserver);
        props.put("mail.smtp.socketFactory.port", emailSMTPPort);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", emailSMTPPort.toString());
        props.put("mail.smtp.timeout", 1000000);
        props.put("mail.smtp.connection timeout", 1000000);
        try {
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(String.join(",", receiverEmail)));
            if ( receiverCCEmail!= null && !receiverCCEmail.isEmpty()) {
                message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(String.join(",", receiverCCEmail)));
            }
            message.setSubject(subject);
            message.setContent(htmlContent, contentType);
            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
