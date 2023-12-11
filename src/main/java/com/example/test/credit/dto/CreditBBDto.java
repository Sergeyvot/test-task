package com.example.test.credit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
public class CreditBBDto {
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfIssue;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate endDate;
    private String typeCredit;
    private Double limit;
    private Double arrears;
    private Double nextPayment;
    private Double psc;
    private Boolean isCurrent;
}
