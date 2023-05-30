package br.com.minsait.report.integration.transaction.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a bank account value object.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BankAccountVO(String id){
}
