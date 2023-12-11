package com.example.test.credit.model;

import com.example.test.credit.TypeCredit;
import com.example.test.user.model.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "credits", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class Credit {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
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
    @Transient
    private Boolean isRecommend;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credit credit = (Credit) o;
        return Double.compare(credit.limit, limit) == 0 && dateOfIssue.equals(credit.dateOfIssue)
                && typeCredit == credit.typeCredit && borrower.equals(credit.borrower);
    }
}
