package br.com.minsait.transaction.service.impl;

import br.com.minsait.transaction.entity.BankAccount;
import br.com.minsait.transaction.entity.BankAccountBalance;
import br.com.minsait.transaction.repository.BankAccountBalanceRepository;
import br.com.minsait.transaction.service.BankAccountBalanceService;
import br.com.minsait.transaction.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The BankAccountBalanceServiceImpl class is an implementation of the BankAccountBalanceService interface.
 * <p>
 * It provides methods for interacting with the BankAccountBalance entity and performing business logic related to bank account balances.
 * <p>
 * This class is annotated with @Service to indicate that it is a service component in the application.
 * <p>
 * The @AllArgsConstructor annotation is used to automatically inject dependencies through constructor-based dependency injection.
 *
 * @see BankAccountBalanceService
 * @see BankAccountBalanceRepository
 * @see BankAccount
 * @author Leonardo Bernardino
 */
@Service
@AllArgsConstructor
public class BankAccountBalanceServiceImpl implements BankAccountBalanceService {

    private final BankAccountBalanceRepository bankAccountBalanceRepository;

    /**

     Retrieves the last bank account balance for the given datetime and bank account.
     The balance is retrieved from the repository by calling the findTopByDatetimeLessThanEqualAndBankAccountOrderByDatetimeDescIdDesc method.
     @param datetime The datetime to search for bank account balance.
     @param bankAccount The bank account to search for.
     @return An Optional containing the last bank account balance, or an empty Optional if not found.
     */
    @Override
    public Optional<BankAccountBalance> findLastByDatetimeAndBankAccount(LocalDateTime datetime, BankAccount bankAccount) {
        return bankAccountBalanceRepository.findTopByDatetimeLessThanEqualAndBankAccountOrderByDatetimeDescIdDesc(datetime, bankAccount);
    }
    /**

     Saves the bank account balance in the repository by calling the save method.
     @param newBankAccountBalance The bank account balance to be saved.
     @return The saved bank account balance.
     */
    @Override
    public BankAccountBalance save(BankAccountBalance newBankAccountBalance) {
        return bankAccountBalanceRepository.save(newBankAccountBalance);
    }

    /**

     Retrieves all bank account balances with a datetime greater than the given datetime and belonging to the specified bank account.
     The balances are retrieved from the repository by calling the findByDatetimeGreaterThanAndBankAccountOrderByDatetimeAsc method.
     @param datetime The datetime to search for bank account balances.
     @param bankAccount The bank account to search for.
     @return A list of bank account balances matching the criteria.
     */
    @Override
    public List<BankAccountBalance> findAllByDatetimeGreaterThanAndBankAccount(LocalDateTime datetime, BankAccount bankAccount) {
        return bankAccountBalanceRepository.findByDatetimeGreaterThanAndBankAccountOrderByDatetimeAsc(datetime, bankAccount);
    }

    /**

     Retrieves the bank account balance for the given datetime and bank account.
     The balance is retrieved from the repository by calling the findByDatetimeAndBankAccount method.
     @param datetime The datetime to search for bank account balance.
     @param bankAccount The bank account to search for.
     @return An Optional containing the bank account balance, or an empty Optional if not found.
     */
    @Override
    public Optional<BankAccountBalance> findByDatetimeAndBankAccount(LocalDateTime datetime, BankAccount bankAccount) {
        return bankAccountBalanceRepository.findByDatetimeAndBankAccount(datetime, bankAccount);
    }
}
