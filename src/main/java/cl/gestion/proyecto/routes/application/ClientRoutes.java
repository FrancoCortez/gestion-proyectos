package cl.gestion.proyecto.routes.application;

import cl.gestion.proyecto.routes.base.BaseRouter;
import cl.gestion.proyecto.service.logic.application.ClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ClientRoutes extends BaseRouter {

    private final String baseRouterPath = this.baseRouter + "client";

    @Bean(value = "client-router")
    public RouterFunction<ServerResponse> router(ClientService clientService) {
        return RouterFunctions.nest(path(baseRouterPath),
                route(POST(""), clientService::insert)
                        .andRoute(PUT("/{id}"), clientService::update)
                        .andRoute(DELETE("/{id}"), clientService::deleteById)
                        .andRoute(GET(""), clientService::findAll)
                        .andRoute(GET("/{id}/find-by"), clientService::findById)
                        .andRoute(DELETE("/{token}/delete-all"), clientService::deleteAll)
        );
    }
}
