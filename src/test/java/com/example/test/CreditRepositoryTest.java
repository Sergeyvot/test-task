package com.example.test;

import com.example.test.credit.TypeCredit;
import com.example.test.credit.dao.CreditRepository;
import com.example.test.credit.model.Credit;
import com.example.test.user.dao.UserRepository;
import com.example.test.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
public class CreditRepositoryTest {

    @Autowired
    private TestEntityManager em;
    @Autowired
    private CreditRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(em);
    }

    @Test
    void testFindAllByBorrowerId() {
        User borrower = userRepository.save(makeUser("90 90 333333", "Иванов Иван Иванович",
                LocalDate.of(1980, 11, 25)));
        Credit credit = repository.save(makeCredit(LocalDate.of(2022, 7, 20),
                LocalDate.of(2025, 7, 20), "CONSUMER", 200000.00, 174614.00,
                5800.00, 19.745, borrower, true));

        TypedQuery<Credit> query = em.getEntityManager()
                .createQuery("Select cr from Credit cr where cr.borrower.passport = concat('', :passport, '') and cr.isCurrent = true", Credit.class);
        List<Credit> result = query.setParameter("passport", borrower.getPassport()).getResultList();

        assertThat(result, hasSize(1));
        assertThat(result.get(0).getId(), notNullValue());
        assertThat(result.get(0).getDateOfIssue(), equalTo(credit.getDateOfIssue()));
        assertThat(result.get(0).getEndDate(), equalTo(credit.getEndDate()));
        assertThat(result.get(0).getTypeCredit(), equalTo(credit.getTypeCredit()));
        assertThat(result.get(0).getLimit(), equalTo(credit.getLimit()));
        assertThat(result.get(0).getArrears(), equalTo(credit.getArrears()));
        assertThat(result.get(0).getNextPayment(), equalTo(credit.getNextPayment()));
        assertThat(result.get(0).getPsc(), equalTo(credit.getPsc()));
        assertThat(result.get(0).getBorrower().getPassport(), equalTo(borrower.getPassport()));
        assertThat(result.get(0).getIsCurrent(), equalTo(credit.getIsCurrent()));
    }

    private User makeUser(String passport, String name, LocalDate dateOfBirth) {

        return User.builder()
                .passport(passport)
                .name(name)
                .dateOfBirth(dateOfBirth).build();
    }

    private Credit makeCredit(LocalDate dateOfIssue, LocalDate endDate, String typeCredit, Double limit,
                              Double arrears, Double nextPayment, Double psc, User borrower, Boolean isCurrent) {

        return Credit.builder()
                .dateOfIssue(dateOfIssue)
                .endDate(endDate)
                .typeCredit(TypeCredit.valueOf(typeCredit))
                .limit(limit)
                .arrears(arrears)
                .nextPayment(nextPayment)
                .psc(psc)
                .borrower(borrower)
                .isCurrent(isCurrent).build();
    }
}
