package br.com.minsait.transaction.entity;

import br.com.minsait.transaction.enumaration.TypeTransaction;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * Represents a transaction entity Payment or Receipt.
 * @author Leonardo Bernardino
 */
@Data
@Entity
@Table(name="transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JoinColumn(nullable = false, name = "fk_bank_account")
    @ManyToOne(fetch = FetchType.LAZY)
    BankAccount bankAccount;

    @Column(nullable = false)
    LocalDateTime creationDateTime;

    @Column(nullable = false)
    LocalDateTime datetime;

    @Column(nullable = false)
    BigDecimal transactionValue;

    @Column(nullable = false, length = 255)
    String description;

    @Column(nullable = true, length = 50)
    String category;

    @Column(nullable = false, length = 50)
    String username;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    TypeTransaction type;



}
