package com.glory.bankapplication.serviceImpl;

import com.glory.bankapplication.dto.AccountInfo;
import com.glory.bankapplication.dto.BankResponse;
import com.glory.bankapplication.dto.BankUserRequestDTO;
import com.glory.bankapplication.dto.EnquiryRequest;
import com.glory.bankapplication.entity.BankUser;
import com.glory.bankapplication.repository.BankUserRepository;
import com.glory.bankapplication.service.UserService;
import com.glory.bankapplication.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service

public class UserServiceImpl implements UserService {       // The userServiceImpl is going to be talking to a db, so I need to inject the userRepo.


    @Autowired
    BankUserRepository bankUserRepository;  // With this injection, access to the repository will be granted




    @Override
    public BankResponse createAccount(BankUserRequestDTO bankUserRequestDTO) {
        /**
         *  Also do validation to check if a user already exist (Has an account), to achieve this, I need to create a Repo Method.
         * In creating an account, you actually want to save the details of a new user into your db. The process of creating a new user is same as instantiating an obj of a new User.
         */

        if (bankUserRepository.existsByEmail(bankUserRequestDTO.getEmail())){
            return  BankResponse.builder()    // Then return a custom message from the bankResponse
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_Message)
                    .accountInfo(null) // This line means that, the user who wanted to create an account doesn't have any account information.
                    .build();

        }

        BankUser newBankUser = BankUser.builder()
                .firstname(bankUserRequestDTO.getFirstname())
                .lastname(bankUserRequestDTO.getLastname())
                .othername(bankUserRequestDTO.getOtherName())
                .gender(bankUserRequestDTO.getGender())
                .address(bankUserRequestDTO.getAddress())
                .stateOfOrigin(bankUserRequestDTO.getStateOfOrigin())
                .email(bankUserRequestDTO.getEmail())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .phoneNumber(bankUserRequestDTO.getPhoneNumber())
                .alternativePhoneNumber(bankUserRequestDTO.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();

        // Next is to save this user to the db.
        BankUser savedUser = bankUserRepository.save(newBankUser);
        // After saving user, we return a valid response message code


        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_EXISTS_Message)

                // Next is to build object of accountInformation.
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())

                        // Then accountName will now be a concatenation of savedUser.getFirstname...
                        .accountName(savedUser.getFirstname() + " " + savedUser.getLastname() + " " + savedUser.getOthername())
                        .build())
                .build();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {

        /**   Steps to Take
         * Check if the provided accountNumber exists in the db.
         */

        Boolean isAccountExist = bankUserRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {                          // If that account doesn't exist
         return BankResponse.builder()                   // return some error messages.
                 // So when the account number doesn't exist......

                 .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                 .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                 .accountInfo(null)
                 .build();
        }

        // But if the Account Number Exist, So you retrieve an Object of the BankUser.

        BankUser foundUser = bankUserRepository.findBankUserByAccountNumber(request.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
                .accountInfo(AccountInfo.builder() // Trying to build balance enquiry below.
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .accountName(foundUser.getFirstname() + " " + foundUser.getLastname() + " " + foundUser.getOthername())

                        .build())

                .build();

    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        return "";
    }

    // Balance Enquiry = Checking how much is available in a particular accountNumber
    // Name Enquiry = Checking which particular name is attach or map to that given particular accountNumber.
    // Crediting the Account, Debiting Account, and also making Transfer.(crediting and debiting is a one way transaction.

    /**
     * the first step for balance Enquiry and Naming Enquiry is by creating a DTO called Enquiry request.
     */

}
