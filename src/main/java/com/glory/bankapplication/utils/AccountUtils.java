package com.glory.bankapplication.utils;

import org.aspectj.bridge.Message;

import java.time.Year;

public class AccountUtils {


    // Creating CUSTOM ERROR MESSAGES :

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_Message = "This user already has an account Created!";

    public static final String ACCOUNT_CREATION_SUCCESS = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = " Account has been successfully created!";

    public static final String ACCOUNT_NOT_EXIST_CODE = "003";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "User with the provided Account Number doesn't exist";

    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_FOUND_SUCCESS = "User Account Found";
    public static final String ACCOUNT_CREDITED_SUCCESS = "005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "User Account was Credited successfully";
    public static final String INSUFFICIENT_BALANCE_CODE = "006";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient Balance in Account";
    public static final String ACCOUNT_DEBITED_SUCCESS = "007";
    public static final String ACCOUNT_DEBITED_SUCCESS_MESSAGE = "Account has been successfully Debited";
    public static final String TRANSFER_SUCCESSFUL_CODE = "008";
    public static final String TRANSFER_SUCCESSFUL_MESSAGE = "Transfer Successful";









    public static String generateAccountNumber(){
        /**
         *  In here, I need to have a method that will handle the creation of accountNumber.
         * By generating the accountNumber, I want the account number to always generate or begin with the current year, plus any random 6digitNumber.
         * 2023 + randomSixDigits.
         */

        Year currentYear = Year.now(); // So this gives the currentYear.
        int min = 100000;
        int max = 999999;

        // next step is to generate a randomNumber btw min and max.
        int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);
        // Convert currentYear and randomNumber to strings, Then concatenate them together.

        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);

        // To now append the currentYear and randomNumber, we use a String Builder
        StringBuilder accountNumber  = new StringBuilder();
        return accountNumber.append(year).append(randomNumber).toString(); // converting and adding toStringBuilder here.

    }



}
