package cl.gestion.proyecto.routes.application;

import cl.gestion.proyecto.routes.base.BaseRouter;
import cl.gestion.proyecto.service.logic.application.ProyectService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProyectRoutes extends BaseRouter {

    private final String baseRouterPath = this.baseRouter + "proyect";

    @Bean(value = "proyect-router")
    public RouterFunction<ServerResponse> router(ProyectService proyectService) {
        return RouterFunctions.nest(path(baseRouterPath),
                route(POST(""), proyectService::insert)
                        .andRoute(PUT("/{id}"), proyectService::update)
                        .andRoute(DELETE("/{id}"), proyectService::deleteById)
                        .andRoute(GET(""), proyectService::findAll)
                        .andRoute(GET("/{id}/find-by"), proyectService::findById)
                        .andRoute(DELETE("/{token}/delete-all"), proyectService::deleteAll)
        );
    }
}
