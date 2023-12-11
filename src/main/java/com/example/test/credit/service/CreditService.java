package com.example.test.credit.service;

import com.example.test.credit.dto.CreditBBDto;
import com.example.test.credit.dto.CreditDto;
import com.example.test.credit.dto.CreditDtoView;
import com.example.test.credit.model.Credit;
import com.example.test.credit.model.CreditBB;

import java.util.List;

public interface CreditService {

    Credit addNewCredit(String userId, CreditDto creditDto);

    CreditBB addNewCreditBB(String userId, CreditBBDto creditBBDto);

    List<CreditDtoView> findRecommendedCredits(String passport);
}
