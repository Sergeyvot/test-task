package com.example.test;

import com.example.test.credit.dto.CreditBBDto;
import com.example.test.credit.dto.CreditDto;
import com.example.test.credit.dto.CreditDtoView;
import com.example.test.credit.service.CreditService;
import com.example.test.user.dto.UserDto;
import com.example.test.user.model.User;
import com.example.test.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

@Transactional
@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CreditServiceImplTest {

    private final EntityManager em;
    private final CreditService creditService;
    private final UserService userService;

    @Test
    void testFindRecommendedCredits() {

        UserDto userDto = makeUserDto("50 50 555555", "Иванов Иван Иванович", LocalDate.of(1980, 11, 25));
        User newUser = userService.addUser(userDto);
        List<CreditDto> sourceCredits = Stream.of(
                makeCreditDto(LocalDate.of(2022, 7, 20), LocalDate.of(2025, 7, 20),
                        "CONSUMER", 200000.00, 174650.00, 5800.00, 19.755,true),
                makeCreditDto(LocalDate.of(2023, 3, 20), LocalDate.of(2028, 3, 20),
                        "OVERDRAFT", 200000.00, 174650.00, 6500.00, 24.755,true)
        ).collect(Collectors.toList());

        for (CreditDto creditDto : sourceCredits) {
            creditService.addNewCredit(newUser.getPassport(), creditDto);
        }

        CreditBBDto creditBBDto = makeCreditBBDto(LocalDate.of(2022, 7, 20), LocalDate.of(2025, 7, 20),
                "CONSUMER", 200000.00, 174650.00, 5800.00, 19.755,true);
        creditService.addNewCreditBB(newUser.getPassport(), creditBBDto);

        List<CreditDtoView> result = creditService.findRecommendedCredits(newUser.getPassport());

        assertThat(result, hasSize(1));
        assertThat(result.get(0).getUuid(), notNullValue());
        assertThat(result.get(0).getDateOfIssue(), equalTo(LocalDate.of(2023, 3, 20)));
        assertThat(result.get(0).getTypeCredit(), equalTo("OVERDRAFT"));
        assertThat(result.get(0).getLimit(), equalTo("200000,00"));
        assertThat(result.get(0).getArrears(), equalTo("174650,00"));
        assertThat(result.get(0).getNextPayment(), equalTo("6500,00"));
        assertThat(result.get(0).getPsc(), equalTo("24,755"));
        assertThat(result.get(0).getRecommendation(), equalTo("ДА"));
    }

    private UserDto makeUserDto(String passport, String name, LocalDate dateOfBirth) {

        return UserDto.builder()
                .passport(passport)
                .name(name)
                .dateOfBirth(dateOfBirth).build();
    }

    private CreditDto makeCreditDto(LocalDate dateOfIssue, LocalDate endDate, String typeCredit, Double limit,
                                  Double arrears, Double nextPayment, Double psc, Boolean isCurrent) {

        return CreditDto.builder()
                .dateOfIssue(dateOfIssue)
                .endDate(endDate)
                .typeCredit(typeCredit)
                .limit(limit)
                .arrears(arrears)
                .nextPayment(nextPayment)
                .psc(psc)
                .isCurrent(isCurrent).build();
    }

    private CreditBBDto makeCreditBBDto(LocalDate dateOfIssue, LocalDate endDate, String typeCredit, Double limit,
                                        Double arrears, Double nextPayment, Double psc, Boolean isCurrent) {

        return CreditBBDto.builder()
                .dateOfIssue(dateOfIssue)
                .endDate(endDate)
                .typeCredit(typeCredit)
                .limit(limit)
                .arrears(arrears)
                .nextPayment(nextPayment)
                .psc(psc)
                .isCurrent(isCurrent).build();
    }
}
