package br.com.minsait.report.service;

import br.com.minsait.report.documents.DailyBalance;
import br.com.minsait.report.integration.transaction.vo.TransactionVO;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service interface for managing daily balances.
 */
public interface DailyBalanceService {
    /**
     * Updates the daily balance for a receipt transaction.
     *
     * @param transaction the receipt transaction
     */
    void updateReceiptDailyBalance(TransactionVO transaction);
    /**
     * Updates the daily balance for a payment transaction.
     *
     * @param transaction the payment transaction
     */
    void updatePaymentDailyBalance(TransactionVO transaction);
    /**
     * Retrieves the daily balance for a specific day and bank account.
     *
     * @param day         the day
     * @param bankAccount the bank account
     * @return the optional daily balance
     */
    Optional<DailyBalance> findByDayAndBankAccount(LocalDate day, String bankAccount);
}
