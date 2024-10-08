package com.glory.bankapplication.serviceImpl;

import com.glory.bankapplication.dto.*;
import com.glory.bankapplication.entity.BankUser;
import com.glory.bankapplication.repository.BankUserRepository;
import com.glory.bankapplication.service.EmailService;
import com.glory.bankapplication.service.UserService;
import com.glory.bankapplication.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class UserServiceImpl implements UserService {       // The userServiceImpl is going to be talking to a db, so I need to inject the userRepo.


    @Autowired
    BankUserRepository bankUserRepository;  // With this injection, access to the repository will be granted.

    @Autowired
    EmailService emailService;


    @Override
    public BankResponseDTO createAccount(BankUserRequestDTO bankUserRequestDTO) { // Balance Enquiry.
        /**
         *  Also do validation to check if a user already exist (Has an account), to achieve this, I need to create a Repo Method.
         * In creating an account, you actually want to save the details of a new user into your db. The process of creating a new user is same as instantiating an obj of a new User.
         */

        if (bankUserRepository.existsByEmail(bankUserRequestDTO.getEmail())) {
            return BankResponseDTO.builder()    // Then return a custom message from the bankResponse
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
        // After saving user, we return a valid response message code.

        /**
         After creating a newUser and Saving the user, then I call that method.
         Create an object of emailDetails
         Send email Alert.
         */
        // Creating an Object of emailDetailsDTO.
        EmailDetailsDTO emailDetailsDTO = EmailDetailsDTO.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("Congratulations! Your Account Has been Successfully Created. \nYour Account Details: \n" +
                        "Account Name: " + savedUser.getFirstname() + " " + savedUser.getLastname() + " " + savedUser.getOthername() + "\nAccount Number: " + savedUser.getAccountNumber())
                .build();

        emailService.sendEmailAlert(emailDetailsDTO); // Call to sending the above emailDetails.


// Normal Response.
        return BankResponseDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)

                // Next is to build object of accountInformation.
                .accountInfo(AccountInfoDTO.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())

                        // Then accountName will now be a concatenation of savedUser.getFirstname...
                        .accountName(savedUser.getFirstname() + " " + savedUser.getLastname() + " " + savedUser.getOthername())
                        .build())
                .build();
    }

    @Override
    public BankResponseDTO balanceEnquiry(EnquiryRequestDTO request) {
        /**   Steps to Take
         * Check if the provided accountNumber exists in the db.
         */

        Boolean isAccountExist = bankUserRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {                          // If that account doesn't exist
            return BankResponseDTO.builder()                   // return some error messages.

                    // So when the account number doesn't exist......
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(AccountInfoDTO.builder() // take notice from this line.
                            .accountNumber(request.getAccountNumber())
                            .build())
                    .build();
        }

        // But if the Account Number Exist, So you retrieve an Object of the BankUser(User).

        BankUser foundUser = bankUserRepository.findByAccountNumber(request.getAccountNumber());
        return BankResponseDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
                .accountInfo(AccountInfoDTO.builder() // Trying to build balance enquiry below.
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .accountName(foundUser.getFirstname() + " " + foundUser.getLastname() + " " + foundUser.getOthername())
                        .build())
                .build();

    }

    @Override
    public String nameEnquiry(EnquiryRequestDTO request) {
        // Given an accountNumber, I want to check if that account Exist

        boolean isAccountExist = bankUserRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;            // Just need to return a string here.
        }

        // But if the Account probably exist, basically create an object of the User.
        BankUser foundUser = bankUserRepository.findByAccountNumber(request.getAccountNumber());

        // After deriving the user, basically return the UserName.
        return foundUser.getFirstname() + " " + foundUser.getLastname() + " " + foundUser.getOthername();

    }

    // Balance Enquiry = Checking how much is available in a particular accountNumber
    // Name Enquiry = Checking which particular name is attach or map to that given particular accountNumber.
    // Crediting the Account, Debiting Account, and also making Transfer.(crediting and debiting is a one way transaction.

    /**
     * the first step for balance Enquiry and Naming Enquiry is by creating a DTO called Enquiry request.
     */

    @Override
    public BankResponseDTO creditAccount(CreditDebitRequestDTO request) {
        // To credit an account:
        //1. Check if the account to be credited exist.
        boolean isAccountExist = bankUserRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {           // but if it doesn't exist.
            return BankResponseDTO.builder()    // return this response messages,Just to tell the client that this account doesn't exist.
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        // If the Account doesn't exist, Just retrieve the current user from the db.
        BankUser userToCredit = bankUserRepository.findByAccountNumber(request.getAccountNumber()); // But if not, I just retrieved the correct User from the Data Base. So the user request will be from the Dto(credit..) which will link a particular accountNumber to a specific user.
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));//Then the next thing to do is to update a particular UserAccount. then the parameter taken in will be userToCredit.get...by adding the existing amount in the DTO to the current value.
        bankUserRepository.save(userToCredit);


        return BankResponseDTO.builder()// Then return the response.
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfoDTO.builder()
                        .accountName(userToCredit.getFirstname() + " " + userToCredit.getLastname() + " " + userToCredit.getOthername())  // This accountName will be from the BankUser userToCredit.
                        .accountBalance(userToCredit.getAccountBalance())
                        .accountNumber(request.getAccountNumber()) // TThe accountNumber is what we usually sent.
                        .build())
                .build();

    }

    @Override
    public BankResponseDTO debitAccount(CreditDebitRequestDTO request) {
        /**
         *  If you are to debit anAccount, there are two methods to check;
         *  1. If the Account exist
         *  2. If the intended amount to be debited is less than the current accountBalance?.
         */

        // This is to check if the AccountDoesn'tExist,
        boolean isAccountExist = bankUserRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return BankResponseDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        // Get the User First to be debited b4 checking if the accountExist.
        BankUser userToDebit = bankUserRepository.findByAccountNumber(request.getAccountNumber());

        /**    Checking if the amount intended to debit is less than the current accountBalance.
         //     Also, I have to use a method to covert the BigDecimal value to Integer
         //     Both for availableBalance and DebitAmount.
         */


        BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger(); // Converting the currentBalance to BigInt.
        BigInteger debitAmount = request.getAmount().toBigInteger(); // Also converted the amount to be debited to BigInt.
        if (availableBalance.intValue() < debitAmount.intValue()) { // For Comparison, convert them both to Int-value.
            return BankResponseDTO.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        else {
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
            bankUserRepository.save(userToDebit);
            return BankResponseDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                    .accountInfo(AccountInfoDTO.builder()
                            .accountNumber(request.getAccountNumber())
                            .accountName(userToDebit.getFirstname() + " " + userToDebit.getLastname() + " " + userToDebit.getOthername())
                            .accountBalance(userToDebit.getAccountBalance())
                            .build())
                    .build();
        }

        // The next Step is if the Account Actually exist

    }

    @Override
    public BankResponseDTO transfer(TransferRequestDTO request) {
        // Getting the account to debit, Also check if it exists.
        // Check if the amount debiting is not more than the current account Balance.
        // Debit the account
        // Get the account to credit.
        // Credit the account.


                // Checking if the account Exists
        boolean isDestinationAccountExist = bankUserRepository.existsByAccountNumber(request.getDestinationAccountNumber()); // verifying the destinationAccountUser if it exists
        if (!isDestinationAccountExist){ // As long as the account exist, you perform action
        return BankResponseDTO.builder() // But if it doesn't exist, you return this Response
                .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                .accountInfo(null)
                .build();
    }
            // Actually comparing the Amount in the saved account to what is requested to be transferred.
        // If the particular amount intend to debit is grater Than the current amount in the account.
        BankUser sourceAccountUser = bankUserRepository.findByAccountNumber(request.getSourceAccountNumber());// Creating an Object.
        if (request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0){ // The compareTo method give you two result, if condition is true(1), if it isn't true(-1)
        return BankResponseDTO.builder()   // So if try withdrawing more than the available amount, you get INSUFFICIENT response message.
                .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                .accountInfo(null)
                .build();

        }

        // Updating the source account with correct details, That is the user which the account is being debited
        sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));


        // Concatenating the sourceUsername to use as variables
        String sourceUsername = sourceAccountUser.getFirstname() + " " + sourceAccountUser.getLastname() + " " +  sourceAccountUser.getOthername();


        bankUserRepository.save(sourceAccountUser); // Saving the update sourceUser in the database



        // Building the email you are actually sending to the debited source.
        EmailDetailsDTO debitAlert = EmailDetailsDTO.builder()
                .subject("DEBIT ALERT")
                .recipient(sourceAccountUser.getEmail())
                .messageBody("The sum of " + request.getAmount() + "has been deducted from your account! Your current balance is " + sourceAccountUser.getAccountBalance())
                .build();

        emailService.sendEmailAlert(debitAlert); // Sending The scent Email

        // Looking for the destination of the accountNumber i'm Checking if it exists or not
        BankUser destinationAccountUser = bankUserRepository.findByAccountNumber(request.getDestinationAccountNumber());

        // Updating the accountBalance
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));
//        String recipientUsername = destinationAccountUSer.getFirstname() + " " + destinationAccountUSer.getLastname() + " " + destinationAccountUSer.getOthername();
        bankUserRepository.save(destinationAccountUser);

        EmailDetailsDTO creditAlert = EmailDetailsDTO.builder()
                .subject("CREDIT ALERT")
                .subject(destinationAccountUser.getEmail())
                .messageBody("The sum of " + request.getAmount() + "has been sent to your account from " + sourceUsername + "Your current balance is " + sourceAccountUser.getAccountBalance())
                .build();

        emailService.sendEmailAlert(debitAlert);

        return BankResponseDTO.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                .accountInfo(null)
                .build();
    }



}
