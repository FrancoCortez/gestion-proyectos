package cl.gestion.proyecto.service.logic.application.implement;

import cl.gestion.proyecto.model.entities.application.ClientEntity;
import cl.gestion.proyecto.model.entities.application.ProyectEntity;
import cl.gestion.proyecto.model.entities.domain.ProyectStateEntity;
import cl.gestion.proyecto.model.request.application.ProyectRequest;
import cl.gestion.proyecto.repository.application.ClientRepository;
import cl.gestion.proyecto.repository.application.ProyectRepository;
import cl.gestion.proyecto.repository.application.UserRepository;
import cl.gestion.proyecto.repository.domain.ProyectStateRepository;
import cl.gestion.proyecto.service.logic.application.ProyectService;
import cl.gestion.proyecto.service.logic.base.implement.BaseServiceImpl;
import cl.gestion.proyecto.service.validator.application.ProyectValidator;
import cl.gestion.proyecto.utils.secure.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProyectServiceImpl extends BaseServiceImpl<ProyectEntity, String> implements ProyectService {

    private final ProyectRepository proyectRepository;
    private final ProyectValidator proyectValidator;
    private final ClientRepository clientRepository;
    private final ProyectStateRepository proyectStateRepository;
    private final UserRepository userRepository;

    public ProyectServiceImpl(final ProyectRepository proyectRepository, final JWTUtils jwtUtils, final ProyectValidator proyectValidator, ClientRepository clientRepository, ProyectStateRepository proyectStateRepository, UserRepository userRepository) {
        super(jwtUtils);
        this.proyectRepository = proyectRepository;
        this.proyectValidator = proyectValidator;
        this.clientRepository = clientRepository;
        this.proyectStateRepository = proyectStateRepository;
        this.userRepository = userRepository;
    }


    public Mono<ServerResponse> insert(ServerRequest request) {
        log.info("Init insert method for Proyect Service.");
        try {
            return request.bodyToMono(ProyectRequest.class).flatMap(
                    resp -> {
                        try {
                            this.proyectValidator.validateRequestProyectI(resp).toFuture().get();
                        } catch (Exception ex) {
                            return this.errorHandler(ex);
                        }
                        log.info("Validacion de la existencia de dependencias en la base de datos.");
                        Mono<ClientEntity> findClient = this.clientRepository.findById(resp.getClientId());
                        Mono<ProyectStateEntity> findStateProyect = this.proyectStateRepository.findById(resp.getProyectStateId());

                        if (findClient.toFuture().join() == null)
                            return this.notFoundHandler("El cliente no existe en la base de datos.");

                        if (findStateProyect.toFuture().join() == null)
                            return this.notFoundHandler("El estado del proyecto no existe en la base de datos.");

                        for (String id : resp.getProyectBoos())
                            if (this.userRepository.findById(id).toFuture().join() == null)
                                return this.notFoundHandler("El jefe de proyecto no existe en el sistema");

                        for (String id : resp.getComercialManager())
                            if (this.userRepository.findById(id).toFuture().join() == null)
                                return this.notFoundHandler("El gerente comercial no existe en el sistema");

                        log.info("Las dependencias de objetos estan correctas.");

                        log.info("Description request proyect : " + resp.toString());
                        log.info("Create proyect entity");
                        ProyectEntity proyectEntity = ProyectEntity.builder().build();
                        BeanUtils.copyProperties(resp, proyectEntity);
                        log.info("Entity create: " + proyectEntity.toString());
                        try {
                            log.info("Generate Auditing for proyect entity");
                            proyectEntity.setAuditing(this.generateAuditingEntity(proyectEntity.getAuditing(), request).toFuture().get());
                            log.info("Auditing create: " + proyectEntity.getAuditing().toString());
                        } catch (Exception ex) {
                            log.error("Error the generate auditing : ex = " + ex.getMessage());
                            return errorHandler(ex);
                        }
                        return ServerResponse.ok().body(this.proyectRepository.insert(proyectEntity), ProyectEntity.class);
                    }
            );
        } catch (Exception ex) {
            return this.errorHandler(ex);
        }
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> deleteAll(ServerRequest request) {
        return null;
    }
}
