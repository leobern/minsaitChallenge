package br.com.minsait.transaction.manager.remove;

import br.com.minsait.transaction.entity.BankAccount;
import br.com.minsait.transaction.entity.BankAccountBalance;
import br.com.minsait.transaction.entity.BankAccountLimit;
import br.com.minsait.transaction.entity.Transaction;
import br.com.minsait.transaction.enumaration.TypeTransaction;
import br.com.minsait.transaction.manager.TransactionManager;
import br.com.minsait.transaction.manager.exception.TransactionException;
import br.com.minsait.transaction.service.BankAccountBalanceService;
import br.com.minsait.transaction.service.BankAccountLimitService;
import br.com.minsait.transaction.service.BankAccountService;
import br.com.minsait.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Class responsible for managing payment transactions.
 * <p>
 * This class implements the TransactionManager interface and contains the necessary services
 * for a transaction execution. It is responsible for remove the transaction,
 * checking if is debited or receipt and saving the account balance
 * changes in accordance with the transaction.
 * <p>
 * The class is annotated with {@link Transactional}
 * and {@link Retryable} to ensure that transactions are
 * correctly rolled back in case of failures and attempts are retried in the case of
 * optimistic locking failures.
 *
 * @author Leonardo.Bernardino
 */
@RequiredArgsConstructor
@Component
public class TransactionRemoveManager implements TransactionManager {

    /**
     * Service for managing transactions.
     */
    private final TransactionService transactionService;
    /**
     * Service for managing bank account balances.
     */
    private final BankAccountBalanceService bankAccountBalanceService;
    /**
     * Service for managing bank accounts.
     */
    private final BankAccountService bankAccountService;
    /**
     * Service for managing bank account limits.
     */
    private final BankAccountLimitService bankAccountLimitService;


    /**
     * Executes a transaction, checks if is debited or receipt and updates the associated bank accounts' balance and limits.
     *
     * @param transaction The transaction to be executed.
     * @throws TransactionException If there is a problem executing the transaction,
     *                              such as when the bank account or its limits are not found,
     *                              or when the new balance would be less than the limit.
     */
    @Transactional(rollbackFor = TransactionException.class )
    @Retryable(value = {OptimisticLockingFailureException.class}, maxAttempts = 3)
    public void execute(Transaction transaction) throws TransactionException {
        // Save transaction
        transactionService.remove(transaction.getId());
        Long bankAccountId = transaction.getBankAccount().getId();
        BankAccount bankAccount = bankAccountService.findById(bankAccountId).orElseThrow(() -> new TransactionException("Bank account not found ID: "+bankAccountId));
        BankAccountLimit bankAccountLimit = bankAccountLimitService.findLastByDatetimeAndBankAccount(transaction.getDatetime(), bankAccount).orElseThrow(() -> new TransactionException("Bank account limit not found for bank account ID: "+bankAccountId));
        BankAccountBalance bankAccountBalance = bankAccountBalanceService.findByDatetimeAndBankAccount(transaction.getDatetime(), bankAccount).orElseThrow(() -> new TransactionException("Bank account balance not found for transaction ID: "+transaction.getId()));
        // Check if there is a limit based on the last balance for the transaction date
        BigDecimal newBalanceValue = calculateNewBalanceAndVerifyLimit(transaction, bankAccountLimit, bankAccountBalance);
        BankAccountBalance newBankAccountBalance = createBankAccountBalance(newBalanceValue,transaction.getBankAccount(),transaction.getDatetime());
        List<BankAccountBalance> allBankAccountBalanceAfterTransaction = bankAccountBalanceService.findAllByDatetimeGreaterThanAndBankAccount(transaction.getDatetime(), bankAccount);
        // Create a chain of balances, to prevent concurrent transactions from using the same previous balance
        if(!allBankAccountBalanceAfterTransaction.isEmpty()){
            newBankAccountBalance.setNextBankAccountBalance(allBankAccountBalanceAfterTransaction.get(0));
        }
        bankAccountBalanceService.save(newBankAccountBalance);
        if(bankAccountBalance.getId()!=null) {
            bankAccountBalance.setNextBankAccountBalance(newBankAccountBalance);
            bankAccountBalanceService.save(bankAccountBalance);
        }
        // Check all subsequent bank accounts, preventing any subsequent balance from being lower than the presented limit
        for (BankAccountBalance bankAccountBalanceAfterTransaction : allBankAccountBalanceAfterTransaction
        ) {
            bankAccountLimit = bankAccountLimitService.findLastByDatetimeAndBankAccount(bankAccountBalanceAfterTransaction.getDatetime(), bankAccount).orElseThrow(() -> new TransactionException("Bank account limit not found"));
            newBalanceValue = calculateNewBalanceAndVerifyLimit(transaction, bankAccountLimit, bankAccountBalanceAfterTransaction);
            bankAccountBalanceAfterTransaction.setBalanceValue(newBalanceValue);
            bankAccountBalanceService.save(bankAccountBalanceAfterTransaction);
        }

    }

    /**
     * Creates a new BankAccountBalance with the provided values.
     *
     * @param balanceValue The balance value for the new account.
     * @param bankAccount The bank account to be associated with the new balance.
     * @param dateTime The date and time when the balance is created.
     * @return The newly created BankAccountBalance.
     */
    private BankAccountBalance createBankAccountBalance(BigDecimal balanceValue, BankAccount bankAccount,LocalDateTime dateTime) {
        BankAccountBalance bankAccountBalance = new BankAccountBalance();
        bankAccountBalance.setBalanceValue(balanceValue);
        bankAccountBalance.setDatetime(dateTime);
        bankAccountBalance.setBankAccount(bankAccount);
        return bankAccountBalance;
    }

    /**
     * Calculates the new balance after a transaction and checks if it is greater than the limit.
     *
     * @param transaction The transaction to be considered.
     * @param bankAccountLimit The limit of the bank account.
     * @param bankAccountBalance The current balance of the bank account.
     * @return The new balance after the transaction.
     * @throws TransactionException If the new balance is less than the limit.
     */
    private BigDecimal calculateNewBalanceAndVerifyLimit(Transaction transaction,BankAccountLimit bankAccountLimit,  BankAccountBalance bankAccountBalance
    ) throws TransactionException {
        BigDecimal limit = bankAccountLimit.getLimit();
        BigDecimal newBalanceValue;

        if(TypeTransaction.PAYMENT.equals(transaction.getType())) {
            newBalanceValue = bankAccountBalance.getBalanceValue().add(transaction.getTransactionValue());
        }else{
            newBalanceValue = bankAccountBalance.getBalanceValue().subtract(transaction.getTransactionValue());
        }
        if (newBalanceValue.compareTo(limit.negate()) < 0) {
            throw new TransactionException("Insufficient balance datetime: "+bankAccountBalance.getDatetime().format(DateTimeFormatter.ofPattern("yyy-MM-dd hh:mm:ss")));
        }
        return newBalanceValue;
    }


}
