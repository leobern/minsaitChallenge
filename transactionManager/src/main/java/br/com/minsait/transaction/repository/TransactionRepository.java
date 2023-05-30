package br.com.minsait.transaction.repository;

import br.com.minsait.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The TransactionRepository interface is responsible for interacting with the database
 * <p>
 * and performing CRUD operations on the Transaction entity.
 * <p>
 * It extends the JpaRepository interface, providing generic database operations support.
 *
 * @see Transaction
 * @see JpaRepository
 * @author Leonardo Bernardino
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
