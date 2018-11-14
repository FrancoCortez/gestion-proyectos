package cl.gestion.proyecto.service.logic.application.implement;

import cl.gestion.proyecto.model.entities.application.ProyectEntity;
import cl.gestion.proyecto.repository.application.ProyectRepository;
import cl.gestion.proyecto.service.logic.application.ProyectService;
import cl.gestion.proyecto.service.logic.base.implement.BaseServiceImpl;
import cl.gestion.proyecto.utils.secure.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProyectServiceImpl extends BaseServiceImpl<ProyectEntity, String> implements ProyectService {

    private final ProyectRepository proyectRepository;

    public ProyectServiceImpl(final ProyectRepository proyectRepository, final JWTUtils jwtUtils) {
        super(jwtUtils);
        this.proyectRepository = proyectRepository;
    }


    public Mono<ServerResponse> insert(ServerRequest request) {
        return null;
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
