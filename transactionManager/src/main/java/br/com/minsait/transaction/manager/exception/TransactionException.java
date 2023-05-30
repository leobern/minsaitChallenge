package br.com.minsait.transaction.manager.exception;


import lombok.AllArgsConstructor;

public class TransactionException extends Throwable{
    public TransactionException(String msg) {
        super(msg);
    }
}
