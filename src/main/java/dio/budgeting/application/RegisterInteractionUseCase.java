package dio.budgeting.application;

import dio.budgeting.application.input.RegisterInteractionInput;
import dio.budgeting.domain.Interaction;
import dio.budgeting.domain.InteractionRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterInteractionUseCase {
    private final InteractionRepository interactionRepository;

    public RegisterInteractionUseCase(InteractionRepository interactionRepository) {
        this.interactionRepository = interactionRepository;
    }

    public void execute(RegisterInteractionInput input) {
        interactionRepository.save(new Interaction(input.prompt(), input.response()));
    }
}
