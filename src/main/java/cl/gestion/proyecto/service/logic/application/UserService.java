package cl.gestion.proyecto.service.logic.application;

import cl.gestion.proyecto.model.entities.application.UserEntity;
import cl.gestion.proyecto.service.logic.base.BaseService;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface UserService extends BaseService<UserEntity, String> {
    Mono<ServerResponse> insert(ServerRequest request);

    Mono<ServerResponse> update(final ServerRequest request);

    Mono<ServerResponse> deleteById(ServerRequest request);

    Mono<ServerResponse> findAll(ServerRequest request);

    Mono<ServerResponse> findById(ServerRequest request);

    Mono<ServerResponse> deleteAll(ServerRequest request);
}
