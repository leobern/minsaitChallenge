package br.com.minsait.transaction.integration;

import br.com.minsait.transaction.entity.Transaction;
import br.com.minsait.transaction.manager.exception.TransactionException;
import br.com.minsait.transaction.vo.TransactionVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

/**
 * Handles the notification of payment transactions to other microservices via Kafka.
 * @author Leonardo Bernardino
 */
@Component
@RequiredArgsConstructor
public class TransactionPaymentKafkaNotify {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(TransactionPaymentKafkaNotify.class);

    /**
     * Notifies the payment transaction to other microservices via Kafka.
     *
     * @param transaction the payment transaction
     * @throws TransactionException if an exception occurs during transaction notification
     */
    @Retryable(value = {TransactionException.class}, maxAttempts = 10)
    public void notifyTransaction(Transaction transaction) throws TransactionException {
        try {
            logger.info("Init notify payment transaction: {}", transaction);

            String transactionJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(transaction);
            kafkaTemplate.send("transactionsPayment", transaction.getId().toString(), transactionJson);
            logger.info("Notified payment transaction: {}", transaction);

        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize transaction ID: {}", transaction.getId(), e);
            throw new TransactionException("Failed to serialize transaction ID: " + transaction.getId());
        }catch (Exception e) {
            logger.error("Failed to notify payment transaction: {}", transaction, e);
            throw new TransactionException("Failed to notify payment transaction ID: " + transaction.getId());
        }


    }

}
