package dio.budgeting.application;

import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class GetTotalRevenueUseCase {
    private final TransactionRepository transactionRepository;

    public GetTotalRevenueUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "get-total-revenue", description = "Obtém o faturamento total da clínica (soma de todas as transações em centavos)")
    public double execute() {
        long totalCents = transactionRepository.findAll().stream()
                .mapToLong(Transaction::getAmount)
                .sum();
        return BigDecimal.valueOf(totalCents).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
