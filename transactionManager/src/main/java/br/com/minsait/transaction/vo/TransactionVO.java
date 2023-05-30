package br.com.minsait.transaction.vo;

import br.com.minsait.transaction.entity.BankAccount;
import br.com.minsait.transaction.entity.Transaction;
import br.com.minsait.transaction.enumaration.TypeTransaction;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The TransactionVO class represents a transfer object for a transaction.
 * It encapsulates the necessary data for creating a transaction.
 * @author Leonardo Bernardino
 */
public record TransactionVO(BankAccount bankAccount, LocalDateTime datetime, BigDecimal transactionValue,
                            String description, String category) {


}