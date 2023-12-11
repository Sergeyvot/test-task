package com.example.test.credit.dto;

import com.example.test.credit.TypeCredit;
import com.example.test.user.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
public class CreditDto {
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
