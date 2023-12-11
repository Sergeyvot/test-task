package com.example.test.controllers;

import com.example.test.credit.dto.CreditDtoView;
import com.example.test.credit.service.CreditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Valid
public class CreditController {
    private final CreditService creditService;

    @GetMapping("/credits")
    public Collection<CreditDtoView> findRecommendedCredits(@NotBlank @RequestParam String passport) {

        List<CreditDtoView> result = creditService.findRecommendedCredits(passport);

        if (result != null) {
            log.info("Запрошен список кредитов заемщика с рекомендациями к рефинансированию. Данные получены");
        } else {
            log.info("Получение списка кредитов заеемщика не выполнено. Необходимо определить ошибку");
        }
        return result;
    }
}
