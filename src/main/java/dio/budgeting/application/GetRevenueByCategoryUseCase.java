package dio.budgeting.application;

import dio.budgeting.domain.Category;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class GetRevenueByCategoryUseCase {
    private final TransactionRepository transactionRepository;

    public GetRevenueByCategoryUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "get-revenue-by-category", description = "Obtém o faturamento ou gasto total de uma categoria específica (soma em centavos)")
    public double execute(@ToolParam(description = "Categoria da transação") Category category) {
        long totalCents = transactionRepository.findAllByCategory(category).stream()
                .mapToLong(Transaction::getAmount)
                .sum();
        return BigDecimal.valueOf(totalCents).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
