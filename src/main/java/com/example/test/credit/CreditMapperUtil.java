package com.example.test.credit;

import com.example.test.credit.dto.CreditBBDto;
import com.example.test.credit.dto.CreditDto;
import com.example.test.credit.dto.CreditDtoView;
import com.example.test.credit.model.Credit;
import com.example.test.credit.model.CreditBB;
import com.example.test.user.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CreditMapperUtil {

    public CreditDtoView toCreditDtoView(Credit credit) {
        CreditDtoView.CreditDtoViewBuilder creditDtoViewBuilder = CreditDtoView.builder();

        creditDtoViewBuilder.uuid(credit.getId().toString());
        creditDtoViewBuilder.dateOfIssue(credit.getDateOfIssue());
        creditDtoViewBuilder.typeCredit(credit.getTypeCredit().toString());
        creditDtoViewBuilder.limit(String.format("%.2f", credit.getLimit()));
        creditDtoViewBuilder.arrears(String.format("%.2f", credit.getArrears()));
        creditDtoViewBuilder.nextPayment(String.format("%.2f", credit.getNextPayment()));
        creditDtoViewBuilder.psc(String.format("%.3f", credit.getPsc()));
        creditDtoViewBuilder.recommendation(credit.getIsRecommend() ? "ДА" : "НЕТ");

        return creditDtoViewBuilder.build();
    }

    public Credit toCredit(CreditBB creditBB) {
        Credit.CreditBuilder creditBuilder = Credit.builder();

        creditBuilder.dateOfIssue(creditBB.getDateOfIssue());
        creditBuilder.endDate(creditBB.getEndDate());
        creditBuilder.typeCredit(creditBB.getTypeCredit());
        creditBuilder.limit(creditBB.getLimit());
        creditBuilder.arrears(creditBB.getArrears());
        creditBuilder.nextPayment(creditBB.getNextPayment());
        creditBuilder.borrower(creditBB.getBorrower());
        creditBuilder.psc(creditBB.getPsc());
        creditBuilder.isCurrent(creditBB.getIsCurrent());

        return creditBuilder.build();
    }

    public Credit toCreditFromCreditDto(CreditDto creditDto, User user) {
        Credit.CreditBuilder creditBuilder = Credit.builder();

        creditBuilder.dateOfIssue(creditDto.getDateOfIssue());
        creditBuilder.endDate(creditDto.getEndDate());
        creditBuilder.typeCredit(TypeCredit.valueOf(creditDto.getTypeCredit()));
        creditBuilder.limit(creditDto.getLimit());
        creditBuilder.arrears(creditDto.getArrears());
        creditBuilder.nextPayment(creditDto.getNextPayment());
        creditBuilder.borrower(user);
        creditBuilder.psc(creditDto.getPsc());
        creditBuilder.isCurrent(creditDto.getIsCurrent());

        return creditBuilder.build();
    }

    public CreditBB toCreditBBFromCreditDto(CreditBBDto creditBBDto, User user) {
        CreditBB.CreditBBBuilder creditBBBuilder = CreditBB.builder();

        creditBBBuilder.dateOfIssue(creditBBDto.getDateOfIssue());
        creditBBBuilder.endDate(creditBBDto.getEndDate());
        creditBBBuilder.typeCredit(TypeCredit.valueOf(creditBBDto.getTypeCredit()));
        creditBBBuilder.limit(creditBBDto.getLimit());
        creditBBBuilder.arrears(creditBBDto.getArrears());
        creditBBBuilder.nextPayment(creditBBDto.getNextPayment());
        creditBBBuilder.borrower(user);
        creditBBBuilder.psc(creditBBDto.getPsc());
        creditBBBuilder.isCurrent(creditBBDto.getIsCurrent());

        return creditBBBuilder.build();
    }
}
