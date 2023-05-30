package br.com.minsait.report.api;


import br.com.minsait.report.documents.DailyBalance;
import br.com.minsait.report.service.DailyBalanceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * CAPI class for retrieving daily balance data for a bank account.
 *
 * @author Leonardo.Bernardino
 */
@RestController
@RequestMapping("/dailyBalance")
@RequiredArgsConstructor
public class DailyBalanceApi {

    private final DailyBalanceService dailyBalanceService;

    private static final Logger log = LoggerFactory.getLogger(DailyBalanceApi.class);

    /**
     *method to find the daily balance for a specific bank account on a given day.
     *
     * @param day The day for which the balance should be found.
     * @param bankAccount The ID of the bank account.
     * @return A response containing the daily balance, or a response indicating that the balance was not found.
     */
    @GetMapping("/find/{day}/{bankAccount}")
    ResponseEntity<DailyBalance> find(@PathVariable("day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate day, @PathVariable("bankAccount")String bankAccount){
        log.info("Requisição para encontrar o saldo diário para a conta {} no dia {}", bankAccount, day);
        Optional<DailyBalance> dailyBalance= dailyBalanceService.findByDayAndBankAccount(day,bankAccount);
        if(dailyBalance.isPresent()){
            log.info("Request to find the daily balance for account {} on day {}", bankAccount, day);
            return ResponseEntity.ok(dailyBalance.get());
        }else{
            log.warn("Daily balance found for account {} on day {}", bankAccount, day);
            return ResponseEntity.notFound().build();
        }
    }
}
