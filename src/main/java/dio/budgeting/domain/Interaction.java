package dio.budgeting.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Interaction {
    private InteractionId id;
    private String prompt;
    private String response;
    private LocalDateTime createdAt;

    public Interaction(String prompt, String response) {
        this.id = new InteractionId();
        this.prompt = prompt;
        this.response = response;
        this.createdAt = LocalDateTime.now();
    }
}
