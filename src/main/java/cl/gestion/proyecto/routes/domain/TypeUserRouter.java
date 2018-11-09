package cl.gestion.proyecto.routes.domain;

import cl.gestion.proyecto.routes.base.BaseRouter;
import cl.gestion.proyecto.service.logic.domain.TypeUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TypeUserRouter extends BaseRouter {
    private String baseRouterPath = this.baseRouter + "type-user";

    @Bean(value = "type-user-router")
    public RouterFunction<ServerResponse> router(TypeUserService typeUserService) {
        return nest(path(baseRouterPath),
                route(POST(""), typeUserService::insert)
                        .andRoute(PUT("/{id}"), typeUserService::update)
                        .andRoute(DELETE("/{id}"), typeUserService::deleteById)
                        .andRoute(DELETE("/{token}/delete-all"), typeUserService::deleteAll)
                        .andRoute(GET(""), typeUserService::findAll)
                        .andRoute(GET("/{id}/find-by"), typeUserService::findById)
                        .andRoute(GET("/{name}/find-name"), typeUserService::findByName)
        );
    }
}
