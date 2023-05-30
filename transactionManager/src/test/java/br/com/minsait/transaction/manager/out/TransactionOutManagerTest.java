package br.com.minsait.transaction.manager.out;

import br.com.minsait.transaction.entity.BankAccount;
import br.com.minsait.transaction.entity.BankAccountBalance;
import br.com.minsait.transaction.entity.BankAccountLimit;
import br.com.minsait.transaction.entity.Transaction;
import br.com.minsait.transaction.manager.exception.TransactionException;
import br.com.minsait.transaction.service.BankAccountBalanceService;
import br.com.minsait.transaction.service.BankAccountLimitService;
import br.com.minsait.transaction.service.BankAccountService;
import br.com.minsait.transaction.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionOutManagerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private BankAccountBalanceService bankAccountBalanceService;

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private BankAccountLimitService bankAccountLimitService;

    private TransactionOutManager transactionOutManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        transactionOutManager = new TransactionOutManager(transactionService, bankAccountBalanceService, bankAccountService, bankAccountLimitService);
    }

    @Test
    void testSuccessfulPaymentTransaction() throws TransactionException {
       
        Transaction transaction = createTransaction();
        BankAccount bankAccount = createBankAccount();
        BankAccountLimit bankAccountLimit = createBankAccountLimit();
        BankAccountBalance bankAccountBalance = createBankAccountBalance(bankAccount.getInitialBalance());

        when(bankAccountService.findById(transaction.getBankAccount().getId())).thenReturn(Optional.of(bankAccount));
        when(bankAccountLimitService.findLastByDatetimeAndBankAccount(transaction.getDatetime(), bankAccount)).thenReturn(Optional.of(bankAccountLimit));
        when(bankAccountBalanceService.findLastByDatetimeAndBankAccount(transaction.getDatetime(), bankAccount)).thenReturn(Optional.of(bankAccountBalance));

        
        assertDoesNotThrow(() -> transactionOutManager.execute(transaction));

        
        verify(bankAccountBalanceService, times(2)).save(any(BankAccountBalance.class));
    }

    @Test
    void testInsufficientBalanceError() {
       
        Transaction transaction = createTransaction();
        BankAccount bankAccount = createBankAccount();
        BankAccountLimit bankAccountLimit = createBankAccountLimit();
        BankAccountBalance bankAccountBalance = createBankAccountBalance(bankAccount.getInitialBalance());

        when(bankAccountService.findById(transaction.getBankAccount().getId())).thenReturn(Optional.of(bankAccount));
        when(bankAccountLimitService.findLastByDatetimeAndBankAccount(transaction.getDatetime(), bankAccount)).thenReturn(Optional.of(bankAccountLimit));
        when(bankAccountBalanceService.findLastByDatetimeAndBankAccount(transaction.getDatetime(), bankAccount)).thenReturn(Optional.of(bankAccountBalance));

        
        assertThrows(TransactionException.class, () -> transactionOutManager.execute(transaction));
        verify(bankAccountBalanceService, never()).save(any(BankAccountBalance.class));
    }

    @Test
    void testBankAccountNotFoundError() {
       
        Transaction transaction = createTransaction();

        when(bankAccountService.findById(transaction.getBankAccount().getId())).thenReturn(Optional.empty());

        
        assertThrows(TransactionException.class, () -> transactionOutManager.execute(transaction));
        verify(transactionService, never()).save(any(Transaction.class));
    }

    @Test
    void testBankAccountLimitNotFoundError() {
       
        Transaction transaction = createTransaction();
        BankAccount bankAccount = createBankAccount();

        when(bankAccountService.findById(transaction.getBankAccount().getId())).thenReturn(Optional.of(bankAccount));
        when(bankAccountLimitService.findLastByDatetimeAndBankAccount(transaction.getDatetime(), bankAccount)).thenReturn(Optional.empty());

        
        assertThrows(TransactionException.class, () -> transactionOutManager.execute(transaction));
        verify(transactionService, never()).save(any(Transaction.class));
    }

    @Test
    void testSuccessfulPaymentTransactionWithSubsequentAccountBalances() throws TransactionException {
       
        Transaction transaction = createTransaction();
        BankAccount bankAccount = createBankAccount();
        BankAccountLimit bankAccountLimit = createBankAccountLimit();
        BankAccountBalance initialBankAccountBalance = createBankAccountBalance(bankAccount.getInitialBalance());
        BankAccountBalance subsequentBankAccountBalance = createBankAccountBalance(new BigDecimal("5000"));

        List<BankAccountBalance> subsequentBalances = new ArrayList<>();
        subsequentBalances.add(subsequentBankAccountBalance);

        when(bankAccountService.findById(transaction.getBankAccount().getId())).thenReturn(Optional.of(bankAccount));
        when(bankAccountLimitService.findLastByDatetimeAndBankAccount(transaction.getDatetime(), bankAccount))
                .thenReturn(Optional.of(bankAccountLimit));
        when(bankAccountBalanceService.findLastByDatetimeAndBankAccount(transaction.getDatetime(), bankAccount))
                .thenReturn(Optional.of(initialBankAccountBalance));
        when(bankAccountBalanceService.findAllByDatetimeGreaterThanAndBankAccount(transaction.getDatetime(), bankAccount))
                .thenReturn(subsequentBalances);

        
        assertDoesNotThrow(() -> transactionOutManager.execute(transaction));

        
        verify(bankAccountBalanceService, times(2)).save(any(BankAccountBalance.class));
    }

    @Test
    void testInsufficientBalanceErrorWithSubsequentAccountBalances() {
       
        Transaction transaction = createTransaction();
        BankAccount bankAccount = createBankAccount();
        BankAccountLimit bankAccountLimit = createBankAccountLimit();
        BankAccountBalance initialBankAccountBalance = createBankAccountBalance(bankAccount.getInitialBalance());
        BankAccountBalance subsequentBankAccountBalance = createBankAccountBalance(new BigDecimal("5000"));

        List<BankAccountBalance> subsequentBalances = new ArrayList<>();
        subsequentBalances.add(subsequentBankAccountBalance);

        when(bankAccountService.findById(transaction.getBankAccount().getId())).thenReturn(Optional.of(bankAccount));
        when(bankAccountLimitService.findLastByDatetimeAndBankAccount(transaction.getDatetime(), bankAccount))
                .thenReturn(Optional.of(bankAccountLimit));
        when(bankAccountBalanceService.findLastByDatetimeAndBankAccount(transaction.getDatetime(), bankAccount))
                .thenReturn(Optional.of(initialBankAccountBalance));
        when(bankAccountBalanceService.findAllByDatetimeGreaterThanAndBankAccount(transaction.getDatetime(), bankAccount))
                .thenReturn(subsequentBalances);

        
        assertThrows(TransactionException.class, () -> transactionOutManager.execute(transaction));
        verify(bankAccountBalanceService, times(1)).save(any(BankAccountBalance.class));
    }

    private Transaction createTransaction() {
        Transaction transaction = new Transaction();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);
        transaction.setBankAccount(bankAccount);
        transaction.setDatetime(LocalDateTime.now());
        transaction.setTransactionValue(new BigDecimal("100"));
        return transaction;
    }

    public BankAccount createBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setUsername("username");
        bankAccount.setName("name");
        bankAccount.setInitialBalance(new BigDecimal("1000.00"));
        bankAccount.setCreationDate(LocalDateTime.now());

        return bankAccount;
    }

    public BankAccountBalance createBankAccountBalance(BigDecimal initValue) {
        BankAccountBalance bankAccountBalance = new BankAccountBalance();
        bankAccountBalance.setId(1L);
        bankAccountBalance.setDatetime(LocalDateTime.now());
        bankAccountBalance.setBalanceValue(initValue);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1l);
        bankAccountBalance.setBankAccount(bankAccount);
        bankAccountBalance.setNextBankAccountBalance(null);
        bankAccountBalance.setVersion(1L);

        return bankAccountBalance;
    }

    public BankAccountLimit createBankAccountLimit() {
        BankAccountLimit bankAccountLimit = new BankAccountLimit();
        bankAccountLimit.setId(1L);
        bankAccountLimit.setDatetime(LocalDateTime.now());
        bankAccountLimit.setLimit(new BigDecimal("500.00"));
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1l);
        bankAccountLimit.setBankAccount(bankAccount);

        return bankAccountLimit;
    }

}
