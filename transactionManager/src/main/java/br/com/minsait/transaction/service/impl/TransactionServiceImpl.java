package br.com.minsait.transaction.service.impl;

import br.com.minsait.transaction.entity.Transaction;
import br.com.minsait.transaction.repository.TransactionRepository;
import br.com.minsait.transaction.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The TransactionServiceImpl class is an implementation of the TransactionService interface.
 * <p>
 * It provides methods for interacting with the Transaction entity and performing business logic related to transactions.
 * <p>
 * This class is annotated with @Service to indicate that it is a service component in the application.
 * <p>
 * The @AllArgsConstructor annotation is used to automatically inject dependencies through constructor-based dependency injection.
 *
 * @author Leonardo Bernardino
 * @see TransactionService
 * @see TransactionRepository
 * @see Transaction
 */
@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    /**
     * Saves a transaction by calling the save method of the TransactionRepository.
     *
     * @param transaction The transaction to be saved.
     * @return The saved transaction.
     */
    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    /**
     * Removes a transaction by its ID using the deleteById method of the TransactionRepository.
     *
     * @param id The ID of the transaction to be removed.
     */
    @Override
    public void remove(Long id) {
        transactionRepository.deleteById(id);
    }

    /**
     * Retrieves a transaction by its ID using the findById method of the TransactionRepository.
     *
     * @param id The ID of the transaction to retrieve.
     * @return An Optional containing the transaction, or an empty Optional if not found.
     */
    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }
}
