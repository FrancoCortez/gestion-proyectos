package cl.gestion.proyecto.repository.application;

import cl.gestion.proyecto.model.entities.application.ProyectEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectRepository extends ReactiveMongoRepository<ProyectEntity, String> {

}
