package com.glory.bankapplication.controller;

import com.glory.bankapplication.dto.*;
import com.glory.bankapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")

public class BankUserController {

    // Here, I need to autowire the service Class.

    @Autowired
    UserService userService;

    @PostMapping
    public BankResponseDTO createAccount(@RequestBody BankUserRequestDTO bankUserRequestDTO){
        return userService.createAccount(bankUserRequestDTO);
    }


    @GetMapping("balanceEnquiry")
    public BankResponseDTO balanceEnquiry(@RequestBody EnquiryRequestDTO request){
        return userService.balanceEnquiry(request);

    }

    @GetMapping("nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequestDTO request){
        return userService.nameEnquiry(request);

    }

    @PostMapping("credit")
    public BankResponseDTO creditAccount(@RequestBody CreditDebitRequestDTO requestDTO){
        return userService.creditAccount(requestDTO);

    }

    @PostMapping("debit")
    public BankResponseDTO debitAccount(@RequestBody CreditDebitRequestDTO requestDTO){
        return userService.debitAccount(requestDTO);

    }

    @PostMapping("transfer")
    public BankResponseDTO transfer(@RequestBody TransferRequestDTO requestDTO){
        return userService.transfer(requestDTO);
    }


}
