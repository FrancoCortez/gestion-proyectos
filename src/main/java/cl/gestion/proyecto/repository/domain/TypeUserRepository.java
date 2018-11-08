package cl.gestion.proyecto.repository.domain;

import cl.gestion.proyecto.model.entities.domain.TypeUserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TypeUserRepository extends ReactiveMongoRepository<TypeUserEntity, String> {

    Flux<TypeUserEntity> findByName(final String name);
}
