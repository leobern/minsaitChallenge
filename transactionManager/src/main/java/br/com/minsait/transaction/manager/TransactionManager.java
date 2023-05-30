package br.com.minsait.transaction.manager;

import br.com.minsait.transaction.entity.Transaction;
import br.com.minsait.transaction.manager.exception.TransactionException;

/**
 * The TransactionManager interface represents the manager responsible for executing transactions.
 * Implementations of this interface handle the execution of transactions,
 * including the validation of transaction data, updating account balances,
 * and enforcing transaction limits.
 *
 * @author Leonardo Bernardino
 * @see Transaction
 * @see TransactionException
 */

public interface TransactionManager {
    /**
     * Executes a transaction.
     *
     * @param transaction The transaction to be executed.
     * @throws TransactionException If there is a problem executing the transaction.
     */
    void execute(Transaction transaction) throws TransactionException;

}
