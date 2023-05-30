package br.com.minsait.transaction.service.impl;

import br.com.minsait.transaction.entity.BankAccount;
import br.com.minsait.transaction.entity.BankAccountLimit;
import br.com.minsait.transaction.repository.BankAccountLimitRepository;
import br.com.minsait.transaction.service.BankAccountLimitService;
import br.com.minsait.transaction.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * The BankAccountLimitServiceImpl class is an implementation of the BankAccountLimitService interface.
 * <p>
 * It provides methods for interacting with the BankAccountLimit entity and performing business logic related to bank account limits.
 * <p>
 * This class is annotated with @Service to indicate that it is a service component in the application.
 * <p>
 * The @AllArgsConstructor annotation is used to automatically inject dependencies through constructor-based dependency injection.
 *
 * @author Leonardo Bernardino
 * @see BankAccountLimitService
 * @see BankAccountLimitRepository
 * @see BankAccount
 */
@Service
@AllArgsConstructor
public class BankAccountLimitServiceImpl implements BankAccountLimitService {

    private final BankAccountLimitRepository bankAccountLimitRepository;

    /**
     * Retrieves the last bank account limit for the given datetime and bank account.
     * The limit is retrieved from the repository by calling the findTopByDatetimeLessThanAndBankAccountOrderByDatetimeDesc method.
     *
     * @param datetime    The datetime to search for bank account limit.
     * @param bankAccount The bank account to search for.
     * @return An Optional containing the last bank account limit, or an empty Optional if not found.
     */
    @Override
    public Optional<BankAccountLimit> findLastByDatetimeAndBankAccount(LocalDateTime datetime, BankAccount bankAccount) {
        return bankAccountLimitRepository.findTopByDatetimeLessThanAndBankAccountOrderByDatetimeDesc(datetime, bankAccount);
    }
}
