package com.glory.bankapplication.service;

import com.glory.bankapplication.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface UserService {                      // This class contains method.
    BankResponseDTO createAccount(BankUserRequestDTO bankUserRequestDTO); // This method is the response return type, taking a parameter of BankUserRequestDTO.

    BankResponseDTO balanceEnquiry(EnquiryRequestDTO enquiryRequest);

    String nameEnquiry(EnquiryRequestDTO enquiryRequest);

    BankResponseDTO creditAccount(CreditDebitRequestDTO request);
    BankResponseDTO debitAccount(CreditDebitRequestDTO request);
    BankResponseDTO transfer(TransferRequestDTO request);

}
