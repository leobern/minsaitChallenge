package br.com.minsait.transaction.service;

import br.com.minsait.transaction.entity.BankAccount;
import br.com.minsait.transaction.entity.BankAccountLimit;

import java.time.LocalDateTime;
import java.util.Optional;


/**
 * The BankAccountLimitService interface defines a method for finding the last bank account limit
 * <p>
 * based on a datetime and bank account.
 *
 * @author Leonardo Bernardino
 * @see BankAccountLimit
 */
public interface BankAccountLimitService {
    /**
     * Finds the last bank account limit for a given datetime and bank account.
     *
     * @param datetime    The datetime for which the limit should be found.
     * @param bankAccount The bank account associated with the limit.
     * @return An Optional containing the last bank account limit, or an empty Optional if not found.
     */
    Optional<BankAccountLimit> findLastByDatetimeAndBankAccount(LocalDateTime datetime, BankAccount bankAccount);
}
