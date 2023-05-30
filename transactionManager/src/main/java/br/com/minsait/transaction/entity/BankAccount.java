package br.com.minsait.transaction.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a bank account entity for transactions.
 * @author Leonardo Bernardino
 */
@Data
@Entity
@Table(name="bank_account")
public class BankAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false,length = 50)
    String username;

    @Column(nullable = false,length = 50)
    String name;

    @Column(nullable = false)
    BigDecimal initialBalance;

    @Column(nullable = false)
    LocalDateTime creationDate;

}
