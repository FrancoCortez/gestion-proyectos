package cl.gestion.proyecto.routes.domain;

import cl.gestion.proyecto.routes.base.BaseRouter;
import cl.gestion.proyecto.service.logic.domain.ProyectStateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProyectStateRouter extends BaseRouter {
    private final String baseRouterPath = this.baseRouter + "proyect-state";

    @Bean(value = "proyect-state-router")
    public RouterFunction<ServerResponse> router(ProyectStateService proyectStateService) {
        return nest(path(baseRouterPath),
                route(POST(""), proyectStateService::insert)
                        .andRoute(PUT("/{id}"), proyectStateService::update)
                        .andRoute(DELETE("/{id}"), proyectStateService::deleteById)
                        .andRoute(DELETE("/{token}/delete-all"), proyectStateService::deleteAll)
                        .andRoute(GET(""), proyectStateService::findAll)
                        .andRoute(GET("/{id}/find-by"), proyectStateService::findById)
                        .andRoute(GET("/{name}/find-name"), proyectStateService::findByName)
        );
    }
}
