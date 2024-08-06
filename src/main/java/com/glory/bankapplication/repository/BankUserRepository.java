package com.glory.bankapplication.repository;

import com.glory.bankapplication.entity.BankUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankUserRepository extends JpaRepository<BankUser, Long> { // Binding this Repository to a particular entity class, taking in generic and the name of the class entity you want to bind with.
      Boolean existsByEmail(String email);   // Checking if a record in a db exist.

      Boolean existsByAccountNumber(String accountNumber);

      BankUser findBankUserByAccountNumber(String accountNumber);




}
