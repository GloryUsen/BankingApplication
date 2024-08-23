package com.glory.bankapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BankResponseDTO {

    /**
     * In this application, I want just a single or uniform response format for every single service.
     */

    private String responseCode;
    private String responseMessage;
    private AccountInfoDTO accountInfo;
}
