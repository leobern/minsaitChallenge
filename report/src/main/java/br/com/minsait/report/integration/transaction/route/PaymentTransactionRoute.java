package br.com.minsait.report.integration.transaction.route;

import br.com.minsait.report.integration.transaction.processor.TransactionPaymentToDayleBalanceProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.DataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Represents the Apache Camel configuration for payment transactions.
 * @author Leonardo Bernardino
 */
@Component
@RequiredArgsConstructor
public class PaymentTransactionRoute extends RouteBuilder {

    private final TransactionPaymentToDayleBalanceProcessor transactionPaymentToDayleBalanceProcessor;
    private static final Logger logger = LoggerFactory.getLogger(PaymentTransactionRoute.class);

    private final DataFormat jsonDataFormat;

    @Value("${kafka.broker}")
    private String broker;
    /**
     * Configures the Apache Camel route for processing payment transactions.
     *
     * @throws Exception if an error occurs during route configuration
     */
    @Override
    public void configure() throws Exception {
        from("kafka:transactionsPayment?brokers="+broker+"&groupId=reporting")
                .unmarshal(jsonDataFormat)
                .process(transactionPaymentToDayleBalanceProcessor);
        logger.info("Configured PaymentTransactionRoute to process payment transactions.");

    }
}
