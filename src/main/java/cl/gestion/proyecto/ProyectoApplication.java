package cl.gestion.proyecto;

import cl.gestion.proyecto.model.entities.base.AuditingEntity;
import cl.gestion.proyecto.model.entities.domain.TypeUserEntity;
import cl.gestion.proyecto.repository.domain.TypeUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
@EnableReactiveMongoRepositories
//@EnableMongoAuditing
@Slf4j
public class ProyectoApplication {

    private final TypeUserRepository typeUserRepository;

    public ProyectoApplication(final TypeUserRepository typeUserRepository) {
        this.typeUserRepository = typeUserRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProyectoApplication.class, args);
    }

    public void run(String... args) throws Exception {
        log.info("Init Data Base");
        this.createTypeUserObject();
        log.info("End Data Base");
    }

    private void createTypeUserObject() {
        ArrayList<TypeUserEntity> list = new ArrayList<>();
        this.typeUserRepository.deleteAll().toFuture().join();
        AuditingEntity auditingEntity = AuditingEntity.builder()
                .version(1L)
                .createdBy("LEGACY")
                .createdDate(new Date())
                .lastModifiedBy("LEGACY")
                .lastModfiedDate(new Date())
                .delete(true)
                .build();
        TypeUserEntity oneObject = TypeUserEntity.builder()
                .name("Administrador")
                .description("Administrador del sistema.")
                .build();
        oneObject.setAuditing(auditingEntity);
        list.add(oneObject);
        TypeUserEntity twoObject = TypeUserEntity.builder()
                .name("Jefe Proyecto")
                .description("Administracion de proyectos")
                .build();
        twoObject.setAuditing(auditingEntity);
        list.add(twoObject);
        this.typeUserRepository.insert(list).subscribe();
    }
}
