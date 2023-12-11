package com.example.test.credit.dao;

import com.example.test.credit.model.CreditBB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreditBBRepository extends JpaRepository<CreditBB, String> {

    @Query("select cr from CreditBB as cr where cr.isCurrent = true " +
            "and cr.borrower.passport = concat('', ?1, '')")
    List<CreditBB> findAllBBByBorrowerId(String passport);
}
