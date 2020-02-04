package com.nhom4.vanphongphamonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom4.vanphongphamonline.model.TaiKhoan;
 
@Controller
public class EmailController {
 
    @Autowired
    public JavaMailSender emailSender;
 
    public void sendEmail(String EmailTo, String subject, String content) {
 
        SimpleMailMessage message = new SimpleMailMessage();
         
        message.setTo(EmailTo);
        message.setSubject(subject);
        message.setText(content);
 
        // Gửi thông tin!
        this.emailSender.send(message);
 
    }
 
}
