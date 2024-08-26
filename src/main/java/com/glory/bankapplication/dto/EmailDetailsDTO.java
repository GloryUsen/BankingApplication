package com.glory.bankapplication.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetailsDTO {
    private String recipient;
    private String messageBody;
    private String subject;
    private String attachment;

}
