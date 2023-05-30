package br.com.minsait.transaction.api;


import br.com.minsait.transaction.entity.Transaction;
import br.com.minsait.transaction.enumaration.TypeTransaction;
import br.com.minsait.transaction.integration.TransactionPaymentKafkaNotify;
import br.com.minsait.transaction.integration.TransactionReceiptKafkaNotify;
import br.com.minsait.transaction.manager.exception.TransactionException;
import br.com.minsait.transaction.manager.in.TransactionInManager;
import br.com.minsait.transaction.manager.out.TransactionOutManager;
import br.com.minsait.transaction.manager.remove.TransactionRemoveManager;
import br.com.minsait.transaction.mapper.TransactionMapper;
import br.com.minsait.transaction.service.TransactionService;
import br.com.minsait.transaction.vo.TransactionVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.TransactionalException;
import java.time.LocalDateTime;
/**
 * Represents the RESTfull API for managing transactions.
 * @author Leonardo Bernardino
 */
@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionApi {

    private static final String SUCCESS = "";
    private static final Logger logger = LoggerFactory.getLogger(TransactionApi.class);

    private final TransactionOutManager transactionOutManager;

    private final TransactionInManager transactionInManager;
    private final TransactionRemoveManager transactionRemoveManager;
    private final TransactionMapper transactionMapper;

    private final TransactionService transactionService;

    private final TransactionPaymentKafkaNotify transactionPaymentKafkaNotify;

    private final TransactionReceiptKafkaNotify transactionReceiptKafkaNotify;
    /**
     * Saves a payment transaction.
     *
     * @param transactionVO The transaction data received from the client.
     * @return ResponseEntity with a success message if the payment transaction is saved successfully,
     *         or a bad request with an error message if an exception occurs.
     */
    @PostMapping("/payment/save")
    ResponseEntity<String> savePayment(@RequestBody TransactionVO transactionVO,@RequestHeader("X-Authenticated-User") String authenticatedUser){
        try {
            logger.info("Init save payment transaction: {}, user:{}", transactionVO,authenticatedUser);
            Transaction transaction = transactionMapper.transactionVOToTransaction(transactionVO);
            transaction.setType(TypeTransaction.PAYMENT);
            transaction.setUsername(authenticatedUser);
            transaction.setCreationDateTime(LocalDateTime.now());
            transactionOutManager.execute(transaction);
            transactionPaymentKafkaNotify.notifyTransaction(transaction);
            logger.info("Saved payment transaction: {}, user:{}", transaction,authenticatedUser);

            return ResponseEntity.ok(SUCCESS);
        }catch (TransactionException transactionException){
            logger.error("Failed to save payment transaction: {}", transactionVO, transactionException);
            return ResponseEntity.badRequest().body(transactionException.getMessage());
        }
    }

    /**
     * Saves a receipt transaction.
     *
     * @param transactionVO The transaction data received from the client.
     * @return ResponseEntity with a success message if the receipt transaction is saved successfully,
     *         or a bad request with an error message if an exception occurs.
     */
    @PostMapping("/receipt/save")
    ResponseEntity<String> saveReceipt(@RequestBody TransactionVO transactionVO,@RequestHeader("X-Authenticated-User") String authenticatedUser){
        try {
            logger.info("Init save receipt transaction: {}, user:{}", transactionVO,authenticatedUser);
            Transaction transaction = transactionMapper.transactionVOToTransaction(transactionVO);
            transaction.setType(TypeTransaction.RECEIPT);
            transaction.setUsername(authenticatedUser);
            transaction.setCreationDateTime(LocalDateTime.now());
            transactionInManager.execute(transaction);
            logger.info("Saved receipt transaction: {}, user:{}", transaction,authenticatedUser);
            transactionReceiptKafkaNotify.notifyTransaction(transaction);
            return ResponseEntity.ok(SUCCESS);
        }catch (TransactionException transactionException){
            logger.error("Failed to save receipt transaction: {}", transactionVO, transactionException);
            return ResponseEntity.badRequest().body(transactionException.getMessage());
        }
    }

    /**
     * Deletes a transaction by ID.
     *
     * @param id The ID of the transaction to be deleted.
     * @return ResponseEntity with a success message if the transaction is deleted successfully,
     *         or a bad request with an error message if the transaction is not found or an exception occurs.
     */
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable("id")Long id,@RequestHeader("X-Authenticated-User") String authenticatedUser){
        try {
            logger.info("init delete transaction with ID: {}, user:{}", id,authenticatedUser);

            Transaction transaction = transactionService.findById(id).orElseThrow(()-> new TransactionException("Transaction not found ID:"+id));
            transactionRemoveManager.execute(transaction);
            logger.info("Deleted transaction with ID: {}, user:{}", id,authenticatedUser);

            return ResponseEntity.ok(SUCCESS);
        }catch (TransactionException transactionException){
            logger.error("Failed to delete transaction with ID: {}, user:{}", id,authenticatedUser, transactionException);
            return ResponseEntity.badRequest().body(transactionException.getMessage());
        }
    }

}
