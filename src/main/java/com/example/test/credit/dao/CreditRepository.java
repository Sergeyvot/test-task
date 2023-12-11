package com.example.test.credit.dao;

import com.example.test.credit.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, String> {

    @Query("select cr from Credit as cr where cr.isCurrent = true " +
            "and cr.borrower.passport = concat('', ?1, '')")
    List<Credit> findAllByBorrowerId(String passport);

}
