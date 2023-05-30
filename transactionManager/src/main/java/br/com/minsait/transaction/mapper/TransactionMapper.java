package br.com.minsait.transaction.mapper;

import br.com.minsait.transaction.entity.Transaction;
import br.com.minsait.transaction.vo.TransactionVO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * The TransactionMapper interface is responsible for mapping between Transaction and TransactionVO objects.
 * It provides methods for converting TransactionVO objects to Transaction objects.
 *
 * @author Leonardo Bernardino
 * @see Transaction
 * @see TransactionVO
 */
@Mapper(componentModel = "spring")
@Component
public interface TransactionMapper {
    /**
     * Converts a TransactionVO object to a Transaction object.
     *
     * @param transactionVO The TransactionVO object to be converted.
     * @return The converted Transaction object.
     */
    Transaction transactionVOToTransaction(TransactionVO transactionVO);
}
