package com.nhom4.vanphongphamonline.controllers;

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

import com.nhom4.vanphongphamonline.models.Account;
import com.nhom4.vanphongphamonline.models.EmailContent;
import com.nhom4.vanphongphamonline.utils.CustomResponse;
 
@Controller
public class EmailController {
 
    @Autowired
    public JavaMailSender emailSender;
    @ResponseBody
    @PostMapping(value = "/api/v1/email/send")
    public ResponseEntity<CustomResponse> sendEmail(@RequestBody EmailContent emailContent) {
 
        SimpleMailMessage message = new SimpleMailMessage();
         
        message.setTo(emailContent.getEmailTo());
        message.setSubject(emailContent.getSubject());
        message.setText(emailContent.getContent());
 
        // Gửi thông tin!
        this.emailSender.send(message);
        return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Gửi thành công", null), HttpStatus.OK);
    }
 
}
