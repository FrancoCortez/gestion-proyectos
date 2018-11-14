package cl.gestion.proyecto.service.logic.application;

import cl.gestion.proyecto.model.entities.application.ClientEntity;
import cl.gestion.proyecto.service.logic.base.BaseService;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface ClientService extends BaseService<ClientEntity, String> {
    Mono<ServerResponse> insert(final ServerRequest request);

    Mono<ServerResponse> update(final ServerRequest request);

    Mono<ServerResponse> deleteById(final ServerRequest request);

    Mono<ServerResponse> findAll(final ServerRequest request);

    Mono<ServerResponse> findById(final ServerRequest request);

    Mono<ServerResponse> deleteAll(final ServerRequest request);
}
