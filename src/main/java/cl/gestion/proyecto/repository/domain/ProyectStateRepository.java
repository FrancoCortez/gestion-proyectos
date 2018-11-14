package cl.gestion.proyecto.repository.domain;

import cl.gestion.proyecto.model.entities.domain.ProyectStateEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProyectStateRepository extends ReactiveMongoRepository<ProyectStateEntity, String> {

    Flux<ProyectStateEntity> findByName(String name);

}
