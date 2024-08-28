package com.glory.bankapplication.service;

import com.glory.bankapplication.dto.EmailDetailsDTO;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendEmailAlert(EmailDetailsDTO emailDetailsDTO); // This method is the response return type, taking a parameter of EmailDetailsDTO.

}
