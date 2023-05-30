package br.com.minsait.transaction.repository;

import br.com.minsait.transaction.entity.BankAccount;
import br.com.minsait.transaction.entity.BankAccountLimit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * The BankAccountLimitRepository interface is responsible for interacting with the database
 * <p>
 * and performing CRUD operations on the BankAccountLimit entity.
 * <p>
 * It extends the JpaRepository interface, providing generic database operations support.
 *
 * @author Leonardo Bernardino
 * @see BankAccountLimit
 * @see JpaRepository
 */
public interface BankAccountLimitRepository extends JpaRepository<BankAccountLimit, Long> {
    /**
     * Retrieves the latest BankAccountLimit record before the specified datetime
     * for the given BankAccount, ordered by datetime in descending order.
     *
     * @param datetime    The datetime to search for.
     * @param bankAccount The BankAccount associated with the BankAccountLimit.
     * @return An Optional containing the BankAccountLimit if found, or an empty Optional.
     */
    Optional<BankAccountLimit> findTopByDatetimeLessThanAndBankAccountOrderByDatetimeDesc(LocalDateTime datetime, BankAccount bankAccount);
}
