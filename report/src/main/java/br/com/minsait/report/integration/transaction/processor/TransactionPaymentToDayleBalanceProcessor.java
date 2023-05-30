package br.com.minsait.report.integration.transaction.processor;

import br.com.minsait.report.integration.transaction.vo.TransactionVO;
import br.com.minsait.report.service.DailyBalanceService;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Processor class for handling payment transactions in Apache Camel, transform transaction in a DailyBalance.
 * @author Leonardo Bernardino
 */
@Component
@RequiredArgsConstructor
public class TransactionPaymentToDayleBalanceProcessor implements Processor {

    private final DailyBalanceService dailyBalanceService;
    private static final Logger logger = LoggerFactory.getLogger(TransactionPaymentToDayleBalanceProcessor.class);

    /**
     * Processes the payment transaction.
     *
     * @param exchange the Apache Camel exchange
     * @throws Exception if an error occurs during processing
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        TransactionVO transaction = exchange.getIn().getBody(TransactionVO.class);
        logger.info("Processing payment transaction: {}", transaction);
        dailyBalanceService.updatePaymentDailyBalance(transaction);
    }
}
