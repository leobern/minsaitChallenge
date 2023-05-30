package br.com.minsait.transaction.service;

import br.com.minsait.transaction.entity.BankAccount;

import java.util.Optional;

/**
 * The BankAccountService interface defines a method for finding a bank account by its ID.
 *
 * @author Leonardo Bernardino
 * @see BankAccount
 */
public interface BankAccountService {

    /**
     * Finds a bank account by its ID.
     *
     * @param id The ID of the bank account.
     * @return An Optional containing the bank account if found, or an empty Optional if not found.
     */
    Optional<BankAccount> findById(Long id);
}
