package com.example.test;

import com.example.test.credit.TypeCredit;
import com.example.test.credit.dao.CreditBBRepository;
import com.example.test.credit.dao.CreditRepository;
import com.example.test.credit.dto.CreditBBDto;
import com.example.test.credit.dto.CreditDto;
import com.example.test.credit.model.Credit;
import com.example.test.credit.model.CreditBB;
import com.example.test.credit.service.CreditService;
import com.example.test.credit.service.CreditServiceImpl;
import com.example.test.user.model.User;
import com.example.test.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CreditServiceTest {

    @Mock
    CreditRepository mockCreditRepository;
    @Mock
    CreditBBRepository mockCreditBBRepository;
    @Mock
    UserService mockUserService;
    CreditService creditService;

    @BeforeEach
    @Test
    void initializingService() {
        creditService = new CreditServiceImpl(mockCreditRepository, mockCreditBBRepository, mockUserService);
    }

    @Test
    void addNewCredit() {

        Mockito
                .when(mockUserService.findUserById(Mockito.anyString()))
                .thenReturn(new User("50 50 555555", "Иванов Иван Иванович", LocalDate.of(1980, 11, 25)));

        Mockito
                .when(mockCreditRepository.save(Mockito.any()))
                .thenReturn(new Credit(UUID.fromString("ac864ed4-bd3d-4ca0-8ba2-b49ec74465ff"), LocalDate.of(2022, 7, 20), LocalDate.of(2025, 7, 20),
                        TypeCredit.CONSUMER, 200000.00, 174650.00, 5800.00, 19.755,
                        new User("50 50 555555", "Иванов Иван Иванович", LocalDate.of(1980, 11, 25)),
                        true, null));

        CreditDto creditDto = CreditDto.builder()
                .dateOfIssue(null)
                .endDate(null)
                .typeCredit("CONSUMER")
                .limit(null)
                .arrears(null)
                .nextPayment(null)
                .psc(null)
                .isCurrent(null).build();

        Credit credit = creditService.addNewCredit("60 60 666666", creditDto);

        Assertions.assertEquals(UUID.fromString("ac864ed4-bd3d-4ca0-8ba2-b49ec74465ff"), credit.getId(), "Поля объектов не совпадают");
        Assertions.assertEquals(LocalDate.of(2022, 7, 20), credit.getDateOfIssue(), "Поля объектов не совпадают");
        Assertions.assertEquals(LocalDate.of(2025, 7, 20), credit.getEndDate(), "Поля объектов не совпадают");
        Assertions.assertEquals(TypeCredit.CONSUMER, credit.getTypeCredit(), "Поля объектов не совпадают");
        Assertions.assertEquals(200000.00, credit.getLimit(), "Поля объектов не совпадают");
        Assertions.assertEquals(174650.00, credit.getArrears(), "Поля объектов не совпадают");
        Assertions.assertEquals(5800.00, credit.getNextPayment(), "Поля объектов не совпадают");
        Assertions.assertEquals(19.755, credit.getPsc(), "Поля объектов не совпадают");
        Assertions.assertNotNull(credit.getBorrower(), "Поля объектов не совпадают");
        Assertions.assertEquals(true, credit.getIsCurrent(), "Поля объектов не совпадают");
    }

    @Test
    void addNewCreditBB() {

        Mockito
                .when(mockUserService.findUserById(Mockito.anyString()))
                .thenReturn(new User("50 50 555555", "Иванов Иван Иванович", LocalDate.of(1980, 11, 25)));

        Mockito
                .when(mockCreditBBRepository.save(Mockito.any()))
                .thenReturn(new CreditBB(1L, LocalDate.of(2022, 7, 20), LocalDate.of(2025, 7, 20),
                        TypeCredit.CONSUMER, 200000.00, 174650.00, 5800.00, 19.755,
                        new User("50 50 555555", "Иванов Иван Иванович", LocalDate.of(1980, 11, 25)),
                        true));

        CreditBBDto creditBBDto = CreditBBDto.builder()
                .dateOfIssue(null)
                .endDate(null)
                .typeCredit("CONSUMER")
                .limit(null)
                .arrears(null)
                .nextPayment(null)
                .psc(null)
                .isCurrent(null).build();

        CreditBB creditBB = creditService.addNewCreditBB("60 60 666666", creditBBDto);

        Assertions.assertEquals(1L, creditBB.getId(), "Поля объектов не совпадают");
        Assertions.assertEquals(LocalDate.of(2022, 7, 20), creditBB.getDateOfIssue(), "Поля объектов не совпадают");
        Assertions.assertEquals(LocalDate.of(2025, 7, 20), creditBB.getEndDate(), "Поля объектов не совпадают");
        Assertions.assertEquals(TypeCredit.CONSUMER, creditBB.getTypeCredit(), "Поля объектов не совпадают");
        Assertions.assertEquals(200000.00, creditBB.getLimit(), "Поля объектов не совпадают");
        Assertions.assertEquals(174650.00, creditBB.getArrears(), "Поля объектов не совпадают");
        Assertions.assertEquals(5800.00, creditBB.getNextPayment(), "Поля объектов не совпадают");
        Assertions.assertEquals(19.755, creditBB.getPsc(), "Поля объектов не совпадают");
        Assertions.assertNotNull(creditBB.getBorrower(), "Поля объектов не совпадают");
        Assertions.assertEquals(true, creditBB.getIsCurrent(), "Поля объектов не совпадают");
    }
}
