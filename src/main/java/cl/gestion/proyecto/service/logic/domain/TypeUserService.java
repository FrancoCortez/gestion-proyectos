package cl.gestion.proyecto.service.logic.domain;

import cl.gestion.proyecto.model.entities.domain.TypeUserEntity;
import cl.gestion.proyecto.service.logic.base.BaseService;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface TypeUserService extends BaseService<TypeUserEntity, String> {

    Mono<ServerResponse> insert(ServerRequest request);
}
