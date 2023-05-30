package br.com.minsait.transaction.integration;

import br.com.minsait.transaction.entity.Transaction;
import br.com.minsait.transaction.manager.exception.TransactionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;


/**
 * Handles the notification of receipt transactions to other microservices via Kafka.
 * @author Leonardo Bernardino
 */
@Component
@RequiredArgsConstructor
public class TransactionReceiptKafkaNotify {
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(TransactionReceiptKafkaNotify.class);

    /**
     * Notifies the receipt transaction to other microservices via Kafka.
     *
     * @param transaction the receipt transaction
     * @throws TransactionException if an exception occurs during transaction notification
     */
    @Retryable(value = {TransactionException.class}, maxAttempts = 10)
    public void notifyTransaction(Transaction transaction) throws TransactionException {
        try {
            logger.info("Init notify receipt transaction: {}", transaction);
            String transactionJson = new ObjectMapper().writeValueAsString(transaction);
            kafkaTemplate.send("transactionsReceipt", transaction.getId().toString(), transactionJson);
            logger.info("Notified receipt transaction: {}", transaction);
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize transaction ID: {}", transaction.getId(), e);
            throw new TransactionException("Failed to serialize transaction ID: " + transaction.getId());
        } catch (Exception e) {
            logger.error("Failed to notify receipt transaction: {}", transaction, e);
            throw new TransactionException("Failed to notify receipt transaction");
        }


    }

}
