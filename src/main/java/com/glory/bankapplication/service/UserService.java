package com.glory.bankapplication.service;

import com.glory.bankapplication.dto.BankResponse;
import com.glory.bankapplication.dto.BankUserRequestDTO;
import com.glory.bankapplication.dto.EnquiryRequest;

public interface UserService {                      // This class contains method.
    BankResponse createAccount(BankUserRequestDTO bankUserRequestDTO); // This method is the response return type, taking a parameter of BankUserRequestDTO.

    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);

    String nameEnquiry(EnquiryRequest enquiryRequest);

}
