package com.glory.bankapplication.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender javaMailSender;;
}
