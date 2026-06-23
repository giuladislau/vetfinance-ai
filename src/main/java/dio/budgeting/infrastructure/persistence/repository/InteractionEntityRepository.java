package dio.budgeting.infrastructure.persistence.repository;

import dio.budgeting.infrastructure.persistence.entity.InteractionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface InteractionEntityRepository extends CrudRepository<InteractionEntity, UUID> {
    List<InteractionEntity> findAllByOrderByCreatedAtDesc();
}
