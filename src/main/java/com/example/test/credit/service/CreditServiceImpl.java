package com.example.test.credit.service;

import com.example.test.credit.CreditMapperUtil;
import com.example.test.credit.dao.CreditBBRepository;
import com.example.test.credit.dao.CreditRepository;
import com.example.test.credit.dto.CreditBBDto;
import com.example.test.credit.dto.CreditDto;
import com.example.test.credit.dto.CreditDtoView;
import com.example.test.credit.model.Credit;
import com.example.test.credit.model.CreditBB;
import com.example.test.credit.model.TableModel;
import com.example.test.user.model.User;
import com.example.test.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository repository;
    private final CreditBBRepository bbRepository;
    private final UserService userService;

    @Override
    public Credit addNewCredit(String userId, CreditDto creditDto) {
        User user = userService.findUserById(userId);
        Credit newCredit = repository.save(CreditMapperUtil.toCreditFromCreditDto(creditDto, user));

        log.info("В базу данных добавлен кредит с UUID {}", newCredit.getId());
        return newCredit;
    }

    @Override
    public CreditBB addNewCreditBB(String userId, CreditBBDto creditBBDto) {
        User user = userService.findUserById(userId);
        CreditBB newCreditBB = bbRepository.save(CreditMapperUtil.toCreditBBFromCreditDto(creditBBDto, user));

        log.info("В базу данных ББ добавлен кредит с id {}", newCreditBB.getId());
        return newCreditBB;
    }

    @Override
    public List<CreditDtoView> findRecommendedCredits(String passport) {

        List<Credit> allCredits = repository.findAllByBorrowerId(passport);
        List<Credit> bbCredits = bbRepository.findAllBBByBorrowerId(passport).stream()
                .map(CreditMapperUtil::toCredit)
                .collect(Collectors.toList());

        List<CreditDtoView> result = allCredits.stream()
                .filter(credit -> !bbCredits.contains(credit))
                .peek(this::checkRecommendation)
                .map(CreditMapperUtil::toCreditDtoView)
                .collect(Collectors.toList());

        displayTable(result);

        return result;
    }

    private Credit checkRecommendation(Credit credit) {

        if (credit.getArrears() <= 0 || credit.getArrears() >= 299000) {
            credit.setIsRecommend(false);
            return credit;
        }
        if (credit.getPsc() < 12.000) {
            credit.setIsRecommend(false);
            return credit;
        }
        if (credit.getNextPayment() == null || credit.getNextPayment() == 0) {
            credit.setIsRecommend(false);
            return credit;
        }
        if (credit.getNextPayment() <= this.getPaymentBB(credit.getArrears())) {
            credit.setIsRecommend(false);
            return credit;
        }

        credit.setIsRecommend(true);
        return credit;
    }

    private double getPaymentBB(double arrears) {
        double payment = 0.003 * arrears + 0.12 / 12 * arrears + 0.014 * arrears;
        return new BigDecimal(payment).setScale(2, RoundingMode.CEILING).doubleValue();
    }

    private void displayTable(List<CreditDtoView> list) {
        if (!GraphicsEnvironment.isHeadless()) {
            JFrame frame = new JFrame("Кредиты, рекомендованные к погашению");
            JTable table = new JTable();
            AbstractTableModel model = new TableModel(list);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setModel(model);
            frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } else {
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-36s %-12s %-13s %-15s %-15s %-16s %-8s %-24s", "НОМЕР", "ДАТА ВЫДАЧИ", "ТИП КРЕДИТА", "ЛИМИТ КРЕДИТА",
                    "ЗАДОЛЖЕННОСТЬ", "СЛЕДУЮЩИЙ ПЛАТЕЖ", "ПСК", "РЕКОМЕНДОВАН К ПОГАШЕНИЮ");
            System.out.println();
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
            for(CreditDtoView credit : list){
                System.out.format("%-36s %-12s %-13s %-15s %-15s %-16s %-8s %-24s",
                        credit.getUuid(), credit.getDateOfIssue(), credit.getTypeCredit(), credit.getLimit(),
                        credit.getArrears(), credit.getNextPayment(), credit.getPsc(), credit.getRecommendation());
                System.out.println();
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }
}
