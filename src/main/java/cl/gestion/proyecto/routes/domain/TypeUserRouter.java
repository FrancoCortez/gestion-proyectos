package cl.gestion.proyecto.routes.domain;

import cl.gestion.proyecto.routes.base.BaseRouter;
import cl.gestion.proyecto.service.logic.domain.TypeUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TypeUserRouter extends BaseRouter {
    private String baseRouterPath = this.baseRouter + "type-user";

    @Bean(value = "type-user-router")
    public RouterFunction<ServerResponse> router(TypeUserService typeUserService) {
        return nest(path(baseRouterPath),
                route(GET(""), typeUserService::insert)
        );
    }
}
