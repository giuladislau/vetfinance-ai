package dio.budgeting.infrastructure.persistence.entity;

import dio.budgeting.domain.Interaction;
import dio.budgeting.domain.InteractionId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InteractionEntity {
    @Id
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String prompt;

    @Column(columnDefinition = "TEXT")
    private String response;

    private LocalDateTime createdAt;

    public static InteractionEntity from(Interaction interaction) {
        return new InteractionEntity(
                interaction.getId().uuid(),
                interaction.getPrompt(),
                interaction.getResponse(),
                interaction.getCreatedAt());
    }

    public Interaction toDomain() {
        return new Interaction(
                new InteractionId(this.id),
                this.prompt,
                this.response,
                this.createdAt
        );
    }
}
