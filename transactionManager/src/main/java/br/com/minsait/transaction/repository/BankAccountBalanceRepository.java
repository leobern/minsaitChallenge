package br.com.minsait.transaction.repository;

import br.com.minsait.transaction.entity.BankAccount;
import br.com.minsait.transaction.entity.BankAccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The BankAccountBalanceRepository interface is responsible for interacting with the database
 * <p>
 * and performing CRUD operations on the BankAccountBalance entity.
 * <p>
 * It extends the JpaRepository interface, providing generic database operations support.
 *
 * @author Leonardo Bernardino
 * @see BankAccountBalance
 * @see JpaRepository
 */
public interface BankAccountBalanceRepository extends JpaRepository<BankAccountBalance, Long> {
    /**
     * Retrieves the latest BankAccountBalance record before or equal to the specified datetime
     * for the given BankAccount, ordered by datetime in descending order.
     *
     * @param datetime    The datetime to search for.
     * @param bankAccount The BankAccount associated with the BankAccountBalance.
     * @return An Optional containing the BankAccountBalance if found, or an empty Optional.
     */
    Optional<BankAccountBalance> findTopByDatetimeLessThanEqualAndBankAccountOrderByDatetimeDescIdDesc(LocalDateTime datetime, BankAccount bankAccount);

    /**
     * Retrieves all BankAccountBalance records after the specified datetime
     * for the given BankAccount, ordered by datetime in ascending order.
     *
     * @param datetime    The datetime to search for.
     * @param bankAccount The BankAccount associated with the BankAccountBalance.
     * @return A list of BankAccountBalance records.
     */
    List<BankAccountBalance> findByDatetimeGreaterThanAndBankAccountOrderByDatetimeAsc(LocalDateTime datetime, BankAccount bankAccount);

    /**
     * Retrieves the BankAccountBalance record for the specified datetime and BankAccount.
     *
     * @param datetime    The datetime to search for.
     * @param bankAccount The BankAccount associated with the BankAccountBalance.
     * @return An Optional containing the BankAccountBalance if found, or an empty Optional.
     */
    Optional<BankAccountBalance> findByDatetimeAndBankAccount(LocalDateTime datetime, BankAccount bankAccount);
}
