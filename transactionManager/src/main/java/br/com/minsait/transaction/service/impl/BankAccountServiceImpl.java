package br.com.minsait.transaction.service.impl;

import br.com.minsait.transaction.entity.BankAccount;
import br.com.minsait.transaction.repository.BankAccountRepository;
import br.com.minsait.transaction.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The BankAccountServiceImpl class is an implementation of the BankAccountService interface.
 * <p>
 * It provides methods for interacting with the BankAccount entity and performing business logic related to bank accounts.
 * <p>
 * This class is annotated with @Service to indicate that it is a service component in the application.
 * <p>
 * The @AllArgsConstructor annotation is used to automatically inject dependencies through constructor-based dependency injection.
 *
 * @see BankAccountService
 * @see BankAccountRepository
 * @see BankAccount
 * @author Leonardo Bernardino
 */
@Service
@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    /**

     Retrieves a bank account by its ID.
     The bank account is retrieved from the repository by calling the findById method.
     @param id The ID of the bank account to retrieve.
     @return An Optional containing the bank account, or an empty Optional if not found.
     */
    @Override
    public Optional<BankAccount> findById(Long id) {
        return bankAccountRepository.findById(id);
    }
}
