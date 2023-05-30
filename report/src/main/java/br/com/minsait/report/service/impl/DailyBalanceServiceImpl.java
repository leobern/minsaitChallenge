package br.com.minsait.report.service.impl;

import br.com.minsait.report.documents.DailyBalance;
import br.com.minsait.report.integration.transaction.vo.TransactionVO;
import br.com.minsait.report.repository.DailyBalanceRepository;
import br.com.minsait.report.service.DailyBalanceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
/**
 * Implementation of the DailyBalanceService interface.
 * @author Leonardo Bernardino
 */
@Service
@RequiredArgsConstructor
public class DailyBalanceServiceImpl implements DailyBalanceService {

    private final DailyBalanceRepository dailyBalanceRepository;
    private static final Logger logger = LoggerFactory.getLogger(DailyBalanceServiceImpl.class);

    /**
     * Updates the daily balance for a receipt transaction.
     *
     * @param transaction the receipt transaction
     */

    @Override
    public void updateReceiptDailyBalance(TransactionVO transaction) {
        logger.info("Init update daily balance for receipt transaction: {}", transaction);
        DailyBalance dailyBalance = dailyBalanceRepository.findByDayAndBankAccount(transaction.datetime().toLocalDate(),transaction.bankAccount().id()).orElseGet(()->createDayleAccount(transaction.datetime().toLocalDate(),transaction.bankAccount().id()));
        BigDecimal transactionValue = transaction.transactionValue();
        dailyBalance.setTotal(dailyBalance.getTotal().add(transactionValue));
        dailyBalance.setReceipts(dailyBalance.getReceipts().add(transactionValue));
        dailyBalanceRepository.save(dailyBalance);
        logger.info("Updated daily balance for receipt transaction: {}", transaction);

    }

    /**
     * Updates the daily balance for a payment transaction.
     *
     * @param transaction the payment transaction
     */
    @Override
    public void updatePaymentDailyBalance(TransactionVO transaction) {
        logger.info("init update daily balance for payment transaction: {}", transaction);

        DailyBalance dailyBalance = dailyBalanceRepository.findByDayAndBankAccount(transaction.datetime().toLocalDate(),transaction.bankAccount().id()).orElseGet(()->createDayleAccount(transaction.datetime().toLocalDate(),transaction.bankAccount().id()));
        BigDecimal transactionValue = transaction.transactionValue();
        dailyBalance.setTotal(dailyBalance.getTotal().subtract(transactionValue));
        dailyBalance.setPayments(dailyBalance.getPayments().add(transactionValue));
        dailyBalanceRepository.save(dailyBalance);
        logger.info("Updated daily balance for payment transaction: {}", transaction);

    }

    /**
     * Retrieves the daily balance for a specific day and bank account.
     *
     * @param day  day of DailyBalance
     * @param bankAccount the bank account
     * @return the optional daily balance
     */
    @Override
    public Optional<DailyBalance> findByDayAndBankAccount(LocalDate day, String bankAccount) {
        return dailyBalanceRepository.findByDayAndBankAccount(day,bankAccount);
    }

    /**
     * Creates a new daily balance entry for the specified day and bank account.
     *
     * @param day  day of DailyBalance
     * @param bankAccount the bank account
     * @return the created daily balance
     */
    private DailyBalance createDayleAccount(LocalDate day, String bankAccount) {
        DailyBalance dailyBalance = new DailyBalance();
        dailyBalance.setBankAccount(bankAccount);
        dailyBalance.setDay(day);
        dailyBalance.setPayments(new BigDecimal(0));
        dailyBalance.setReceipts(new BigDecimal(0));
        dailyBalance.setTotal(new BigDecimal(0));
        return dailyBalanceRepository.save(dailyBalance);


    }
}
