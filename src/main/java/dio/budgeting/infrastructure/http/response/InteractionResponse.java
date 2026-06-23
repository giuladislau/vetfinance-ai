package dio.budgeting.infrastructure.http.response;

import dio.budgeting.application.output.InteractionOutput;

import java.time.LocalDateTime;

public record InteractionResponse(String id, String prompt, String response, LocalDateTime createdAt) {
    public static InteractionResponse from(InteractionOutput output) {
        return new InteractionResponse(output.id(), output.prompt(), output.response(), output.createdAt());
    }
}
