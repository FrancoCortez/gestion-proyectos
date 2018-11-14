package cl.gestion.proyecto.service.logic.domain.implement;

import cl.gestion.proyecto.model.entities.domain.ProyectStateEntity;
import cl.gestion.proyecto.repository.domain.ProyectStateRepository;
import cl.gestion.proyecto.service.logic.base.implement.BaseServiceImpl;
import cl.gestion.proyecto.service.logic.domain.ProyectStateService;
import cl.gestion.proyecto.utils.secure.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProyectStateServiceImpl extends BaseServiceImpl<ProyectStateEntity, String> implements ProyectStateService {

    private final ProyectStateRepository proyectStateRepository;

    public ProyectStateServiceImpl(final JWTUtils jwtUtils, final ProyectStateRepository proyectStateRepository) {
        super(jwtUtils);
        this.proyectStateRepository = proyectStateRepository;
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

    public Mono<ServerResponse> deleteAll(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> findByName(ServerRequest request) {
        return null;
    }
}
