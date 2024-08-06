package com.glory.bankapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BankUserRequestDTO {

    /**
     * A DTO is a data transfer object, it helps to limit exposure of some fields to the users from the entity class.
     * Also helps in separating object creation from the entity itself.
     */

    private String firstname;
    private String lastname;
    private String otherName;
    private String gender;
    private String address;
    private String stateOfOrigin;
    private String email;
    private String phoneNumber;
    private String alternativePhoneNumber;
}
