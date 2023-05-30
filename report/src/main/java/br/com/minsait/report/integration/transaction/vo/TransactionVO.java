package br.com.minsait.report.integration.transaction.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a transaction value object.
 * @author Leonardo Bernardino
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionVO(BankAccountVO bankAccount, LocalDateTime datetime, BigDecimal transactionValue) {




}