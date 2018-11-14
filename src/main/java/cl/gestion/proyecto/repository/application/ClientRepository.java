package cl.gestion.proyecto.repository.application;

import cl.gestion.proyecto.model.entities.application.ClientEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<ClientEntity, String> {

}
