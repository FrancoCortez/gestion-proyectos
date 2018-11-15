package cl.gestion.proyecto.handler.auth.implement;

import cl.gestion.proyecto.handler.auth.AuthHandler;
import cl.gestion.proyecto.model.request.application.LoginRequest;
import cl.gestion.proyecto.model.request.application.RegisterRequest;
import cl.gestion.proyecto.service.logic.auth.implement.AuthServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class AuthHandlerImpl implements AuthHandler {

    private final AuthServiceImpl authService;

    public AuthHandlerImpl(AuthServiceImpl authService) {
        this.authService = authService;
    }


    public Mono<ServerResponse> login(final ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(resp -> {
                    return authService.login2(resp).flatMap(response -> {
                        if (response.getStatus() == 200) {
                            return ServerResponse
                                    .ok()
                                    .header("Authorization", response
                                            .getData()
                                            .toFuture()
                                            .join()
                                            .toString())
                                    .build();
                        } else {
                            return ServerResponse
                                    .status((Integer) response.getStatus())
                                    .build();
                        }
                    });
                });
    }

    public Mono<ServerResponse> register(final ServerRequest request) {
        return request.bodyToMono(RegisterRequest.class)
                .flatMap(resp -> {
                    return this.authService.register2(resp).flatMap(response -> {
                        return ServerResponse.status(response.getStatus()).body(response.getData(), response.getCla());
                    });
                });
    }

}
