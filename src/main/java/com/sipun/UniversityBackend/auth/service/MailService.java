package com.sipun.UniversityBackend.auth.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendUserCredentials(String email, String temporaryPassword) {
        try{

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your New Account Credentials");
        message.setText("Hello,\n\nYour account has been created successfully. Use the following credentials to log in:\n\n"
                + "Email: " + email + "\n"
                + "Password: " + temporaryPassword + "\n\n"
                + "Please log in and complete your profile.\n\n"
                + "Best regards,\nUniversity ERP Team");

        mailSender.send(message);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
