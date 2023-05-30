package br.com.minsait.transaction.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * Represents a bank account limit entity for bank account.
 * @author Leonardo Bernardino
 */
@Data
@Entity
@Table(name="bank_account_limit")
public class BankAccountLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    LocalDateTime datetime;
    @Column(nullable = false)
    BigDecimal limit;
    @JoinColumn(nullable = false, name = "fk_bank_account")
    @ManyToOne(fetch = FetchType.LAZY)
    BankAccount bankAccount;

}
