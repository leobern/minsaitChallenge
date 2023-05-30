package br.com.minsait.report.documents;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
/**
 * Represents the daily balance of a cash flow.
 * @author Leonardo Bernardino
 */
@Data
@Document(collection = "dailyBalance")
public class DailyBalance {

    @Id
    String id;
    BigDecimal total;
    BigDecimal payments;
    BigDecimal receipts;
    String bankAccount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate day;

    Long version;
}
