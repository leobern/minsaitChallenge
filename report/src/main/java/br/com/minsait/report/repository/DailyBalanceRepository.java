package br.com.minsait.report.repository;

import br.com.minsait.report.documents.DailyBalance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyBalanceRepository extends MongoRepository<DailyBalance,String> {
    Optional<DailyBalance> findByDayAndBankAccount(LocalDate day, String bankAccount);
}
