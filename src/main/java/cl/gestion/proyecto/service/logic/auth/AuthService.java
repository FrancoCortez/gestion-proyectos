package cl.gestion.proyecto.service.logic.auth;

import cl.gestion.proyecto.model.entities.application.UserEntity;
import cl.gestion.proyecto.service.logic.base.BaseService;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface AuthService extends BaseService<UserEntity, String> {

    Mono<ServerResponse> login(ServerRequest request);

    Mono<ServerResponse> register(ServerRequest request);
}
