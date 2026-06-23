package dio.budgeting.domain;

import java.util.List;

public interface InteractionRepository {
    Interaction save(Interaction interaction);
    List<Interaction> findAllDesc();
}
