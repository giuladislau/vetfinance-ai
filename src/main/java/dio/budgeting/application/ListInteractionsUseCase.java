package dio.budgeting.application;

import dio.budgeting.application.output.InteractionOutput;
import dio.budgeting.domain.InteractionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListInteractionsUseCase {
    private final InteractionRepository interactionRepository;

    public ListInteractionsUseCase(InteractionRepository interactionRepository) {
        this.interactionRepository = interactionRepository;
    }

    public List<InteractionOutput> execute() {
        return interactionRepository.findAllDesc().stream().map(InteractionOutput::from).toList();
    }
}
