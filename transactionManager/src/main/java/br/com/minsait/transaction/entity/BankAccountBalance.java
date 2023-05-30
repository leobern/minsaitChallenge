package br.com.minsait.transaction.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a bank account balance entity for transactions.
 * @author Leonardo Bernardino
 */
@Data
@Entity
@Table(name = "bank_account_balance")
@NoArgsConstructor
public class BankAccountBalance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    LocalDateTime datetime;
    @Column(nullable = false)
    BigDecimal balanceValue;
    @JoinColumn(nullable = false, name = "fk_bank_account")
    @ManyToOne(fetch = FetchType.LAZY)
    BankAccount bankAccount;

    @JoinColumn(nullable = true, name = "fk_next_bank_account_balance")
    @ManyToOne(fetch = FetchType.LAZY)
    BankAccountBalance nextBankAccountBalance;
    @Version
    Long version;

}
