package dio.budgeting.domain;

import java.util.UUID;

public record InteractionId(UUID uuid) {
    public InteractionId() {
        this(UUID.randomUUID());
    }
}
