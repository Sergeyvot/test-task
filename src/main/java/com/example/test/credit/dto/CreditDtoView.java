package com.example.test.credit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
public class CreditDtoView {
    private String uuid;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfIssue;
    private String typeCredit;
    private String limit;
    private String arrears;
    private String nextPayment;
    private String psc;
    private String recommendation;
}
