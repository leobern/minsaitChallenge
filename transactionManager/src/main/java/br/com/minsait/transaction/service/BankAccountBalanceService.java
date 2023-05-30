package br.com.minsait.transaction.service;

import br.com.minsait.transaction.entity.BankAccount;
import br.com.minsait.transaction.entity.BankAccountBalance;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The BankAccountBalanceService interface defines methods for managing bank account balances.
 * <p>
 * It provides operations to find, save, and retrieve bank account balance information.
 *
 * @author Leonardo Bernardino
 * @see BankAccountBalance
 */
public interface BankAccountBalanceService {
    /**
     * Finds the last bank account balance for a given datetime and bank account.
     *
     * @param datetime    The datetime for which the balance should be found.
     * @param bankAccount The bank account associated with the balance.
     * @return An Optional containing the last bank account balance, or an empty Optional if not found.
     */
    Optional<BankAccountBalance> findLastByDatetimeAndBankAccount(LocalDateTime datetime, BankAccount bankAccount);

    /**
     * Saves a new bank account balance.
     *
     * @param newBankAccountBalance The new bank account balance to be saved.
     * @return The saved bank account balance.
     */
    BankAccountBalance save(BankAccountBalance newBankAccountBalance);

    /**
     * Retrieves all bank account balances with a datetime greater than the specified datetime for a given bank account.
     *
     * @param datetime    The datetime from which the balances should be retrieved.
     * @param bankAccount The bank account associated with the balances.
     * @return A list of bank account balances with datetimes greater than the specified datetime.
     */
    List<BankAccountBalance> findAllByDatetimeGreaterThanAndBankAccount(LocalDateTime datetime, BankAccount bankAccount);

    /**
     * Finds a bank account balance for a specific datetime and bank account.
     *
     * @param datetime    The datetime for which the balance should be retrieved.
     * @param bankAccount The bank account associated with the balance.
     * @return An Optional containing the bank account balance, or an empty Optional if not found.
     */
    Optional<BankAccountBalance> findByDatetimeAndBankAccount(LocalDateTime datetime, BankAccount bankAccount);
}
