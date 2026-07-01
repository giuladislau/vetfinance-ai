package dio.budgeting.infrastructure.http;

import dio.budgeting.application.GetDashboardUseCase;
import dio.budgeting.application.output.DashboardOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final GetDashboardUseCase getDashboardUseCase;

    public DashboardController(GetDashboardUseCase getDashboardUseCase) {
        this.getDashboardUseCase = getDashboardUseCase;
    }

    @GetMapping
    public ResponseEntity<DashboardOutput> getDashboard() {
        return ResponseEntity.ok(getDashboardUseCase.execute());
    }
}
