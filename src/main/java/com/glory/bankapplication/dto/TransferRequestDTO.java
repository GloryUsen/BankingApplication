package com.glory.bankapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDTO {
    /**
     * In other to perform a transfer, 3-steps will be observed.
     */

    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private BigDecimal amount;
}
