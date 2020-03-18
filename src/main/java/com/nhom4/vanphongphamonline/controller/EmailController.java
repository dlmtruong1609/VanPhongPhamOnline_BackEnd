package com.nhom4.vanphongphamonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom4.vanphongphamonline.model.Account;
import com.nhom4.vanphongphamonline.services.ServiceStatus;
import com.nhom4.vanphongphamonline.utils.EmailContent;
 
@Controller
public class EmailController {
 
    @Autowired
    public JavaMailSender emailSender;
    @ResponseBody
    @PostMapping(value = "/api/v1/email/send")
    public ResponseEntity<ServiceStatus> sendEmail(@RequestBody EmailContent emailContent) {
 
        SimpleMailMessage message = new SimpleMailMessage();
         
        message.setTo(emailContent.getEmailTo());
        message.setSubject(emailContent.getSubject());
        message.setText(emailContent.getContent());
 
        // Gửi thông tin!
        this.emailSender.send(message);
        return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Gửi thành công"), HttpStatus.OK);
    }
 
}
