package com.glory.bankapplication.controller;

import com.glory.bankapplication.dto.BankResponse;
import com.glory.bankapplication.dto.BankUserRequestDTO;
import com.glory.bankapplication.dto.EnquiryRequest;
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
    public BankResponse createAccount(@RequestBody BankUserRequestDTO bankUserRequestDTO){
        return userService.createAccount(bankUserRequestDTO);
    }
//
//    @GetMapping("nameEnquiry")
//    public String nameEnquiry(@RequestBody EnquiryRequest enquiryRequest){
//        return userService.nameEnquiry(enquiryRequest);
//
//    }
//
//    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
//        return userService.balanceEnquiry(enquiryRequest);
//
//    }


}
