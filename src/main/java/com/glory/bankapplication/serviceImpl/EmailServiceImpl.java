package com.glory.bankapplication.serviceImpl;

import com.glory.bankapplication.dto.EmailDetailsDTO;
import com.glory.bankapplication.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;;

    /**
     * The next step to add emailSender as an instance Variable with global access, so that it can be called elsewhere.
     Using @value to be able to have access to the application.properties file. With the mail. Username,
     That, I'll have access to the "Login user of the SMTP server".
     */

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public void sendEmailAlert(EmailDetailsDTO emailDetailsDTO){
        try {


            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(senderEmail); // This line shows who are the mailSender(who is the message coming from).
            mailMessage.setTo(emailDetailsDTO.getRecipient()); // Who is receiving the message?.
            mailMessage.setText(emailDetailsDTO.getMessageBody()); // Body or details of the message.
            mailMessage.setSubject(emailDetailsDTO.getSubject()); // Subject heading of email.

            javaMailSender.send(mailMessage);
            System.out.println("Mail Sent successfully");
        } catch (MailException e) {
            throw new RuntimeException(e); // Adding the corresponding exception.
        }

    }
}
