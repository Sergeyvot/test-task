package com.example.test.credit.model;

import com.example.test.credit.TypeCredit;
import com.example.test.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "credits_bb", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class CreditBB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "type_credit")
    @Enumerated(EnumType.STRING)
    private TypeCredit typeCredit;
    @Column(name = "limit_credits")
    private Double limit;
    private Double arrears;
    @Column(name = "next_payment")
    private Double nextPayment;
    private Double psc;
    @ManyToOne
    @JoinColumn(name = "borrower_id")
    @ToString.Exclude
    private User borrower;
    @Column(name = "is_current")
    private Boolean isCurrent;
}
