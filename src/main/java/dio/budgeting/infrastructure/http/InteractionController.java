package dio.budgeting.infrastructure.http;

import dio.budgeting.application.ListInteractionsUseCase;
import dio.budgeting.infrastructure.http.response.InteractionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audits")
public class InteractionController {
    private final ListInteractionsUseCase listInteractionsUseCase;

    public InteractionController(ListInteractionsUseCase listInteractionsUseCase) {
        this.listInteractionsUseCase = listInteractionsUseCase;
    }

    @GetMapping
    public List<InteractionResponse> listAudits() {
        return listInteractionsUseCase.execute().stream().map(InteractionResponse::from).toList();
    }
}
