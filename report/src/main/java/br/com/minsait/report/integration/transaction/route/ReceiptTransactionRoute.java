package br.com.minsait.report.integration.transaction.route;

import br.com.minsait.report.integration.transaction.processor.TransactionReceiptToDayleBalanceProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.DataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Represents the Apache Camel configuration for receipt transactions.
 * @author Leonardo Bernardino
 */
@Component
@RequiredArgsConstructor
public class ReceiptTransactionRoute  extends RouteBuilder {

    private final TransactionReceiptToDayleBalanceProcessor transactionReceiptToDayleBalanceProcessor;
    private static final Logger logger = LoggerFactory.getLogger(ReceiptTransactionRoute.class);

    private final DataFormat jsonDataFormat;

    @Value("${kafka.broker}")
    private String broker;

    /**
     * Configures the Apache Camel route for processing receipt transactions.
     *
     * @throws Exception if an error occurs during route configuration
     */
    @Override
    public void configure() throws Exception {
        from("kafka:transactionsReceipt?brokers="+broker+"&groupId=reporting")
                .unmarshal(jsonDataFormat)
                .process(transactionReceiptToDayleBalanceProcessor);

        logger.info("Configured ReceiptTransactionRoute to process receipt transactions.");

    }
}
