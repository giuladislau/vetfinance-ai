package dio.budgeting.infrastructure.persistence.repository;

import dio.budgeting.domain.Interaction;
import dio.budgeting.domain.InteractionRepository;
import dio.budgeting.infrastructure.persistence.entity.InteractionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaInteractionRepository implements InteractionRepository {
    private final InteractionEntityRepository interactionEntityRepository;

    public JpaInteractionRepository(InteractionEntityRepository interactionEntityRepository) {
        this.interactionEntityRepository = interactionEntityRepository;
    }

    @Override
    public Interaction save(Interaction interaction) {
        var entity = InteractionEntity.from(interaction);
        return interactionEntityRepository.save(entity).toDomain();
    }

    @Override
    public List<Interaction> findAllDesc() {
        return interactionEntityRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(InteractionEntity::toDomain)
                .toList();
    }
}
