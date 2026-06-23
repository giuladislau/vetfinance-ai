package dio.budgeting.application.output;

import dio.budgeting.domain.Interaction;

import java.time.LocalDateTime;

public record InteractionOutput(String id, String prompt, String response, LocalDateTime createdAt) {
    public static InteractionOutput from(Interaction interaction) {
        return new InteractionOutput(
                interaction.getId().uuid().toString(),
                interaction.getPrompt(),
                interaction.getResponse(),
                interaction.getCreatedAt()
        );
    }
}
