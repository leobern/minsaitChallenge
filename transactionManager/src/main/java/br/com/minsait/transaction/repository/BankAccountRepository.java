package br.com.minsait.transaction.repository;

import br.com.minsait.transaction.entity.BankAccount;
import br.com.minsait.transaction.entity.BankAccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;


/**
 * The BankAccountRepository interface is responsible for interacting with the database
 * <p>
 * and performing CRUD operations on the BankAccount entity.
 * <p>
 * It extends the JpaRepository interface, providing generic database operations support.
 *
 * @see BankAccount
 * @see JpaRepository
 * @author Leonardo Bernardino
 */
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
