package dio.budgeting.application;

import dio.budgeting.application.output.DashboardOutput;
import dio.budgeting.domain.Category;
import dio.budgeting.domain.InteractionRepository;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GetDashboardUseCase {
    private final TransactionRepository transactionRepository;
    private final InteractionRepository interactionRepository;

    public GetDashboardUseCase(TransactionRepository transactionRepository, InteractionRepository interactionRepository) {
        this.transactionRepository = transactionRepository;
        this.interactionRepository = interactionRepository;
    }

    public DashboardOutput execute() {
        List<Transaction> transactions = transactionRepository.findAll();
        long totalAudits = interactionRepository.findAllDesc().size();

        long totalTransactions = transactions.size();

        long totalCents = transactions.stream()
                .mapToLong(Transaction::getAmount)
                .sum();
        double totalRevenue = BigDecimal.valueOf(totalCents).setScale(2, RoundingMode.HALF_UP).doubleValue();

        String topCategory = "Nenhuma transação registrada";
        long topCategoryTransactions = 0;

        if (!transactions.isEmpty()) {
            Map.Entry<Category, Long> topEntry = transactions.stream()
                    .collect(Collectors.groupingBy(Transaction::getCategory, Collectors.counting()))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .orElse(null);

            if (topEntry != null) {
                topCategory = topEntry.getKey().name();
                topCategoryTransactions = topEntry.getValue();
            }
        }

        return new DashboardOutput(
                totalRevenue,
                totalTransactions,
                topCategory,
                topCategoryTransactions,
                totalAudits
        );
    }
}
