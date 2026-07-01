package dio.budgeting.application.output;

public record DashboardOutput(
        double totalRevenue,
        long totalTransactions,
        String topCategory,
        long topCategoryTransactions,
        long totalAudits
) {
}
