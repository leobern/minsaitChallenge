package br.com.minsait.transaction.service;

import br.com.minsait.transaction.entity.Transaction;

import java.util.Optional;

/**
 * The TransactionService interface provides methods for managing transactions.
 *
 * @author Leonardo Bernardino
 * @see Transaction
 */
public interface TransactionService {
    /**
     * Saves a transaction.
     *
     * @param transaction The transaction to be saved.
     * @return The saved transaction.
     */
    Transaction save(Transaction transaction);

    /**
     * Removes a transaction by its ID.
     *
     * @param id The ID of the transaction to be removed.
     */
    void remove(Long id);

    /**
     * Finds a transaction by its ID.
     *
     * @param id The ID of the transaction.
     * @return An Optional containing the transaction if found, or an empty Optional if not found.
     */
    Optional<Transaction> findById(Long id);
}
