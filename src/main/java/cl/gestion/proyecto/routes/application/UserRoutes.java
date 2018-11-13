package cl.gestion.proyecto.routes.application;

import cl.gestion.proyecto.routes.base.BaseRouter;
import cl.gestion.proyecto.service.logic.application.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRoutes extends BaseRouter {

    private final String baseRouterPath = this.baseRouter + "user";

    @Bean(value = "user-router")
    public RouterFunction<ServerResponse> router(UserService userService) {
        return RouterFunctions.nest(path(baseRouterPath),
                route(POST(""), userService::insert)
                        .andRoute(PUT("/{id}"), userService::update)
                        .andRoute(DELETE("/{id}"), userService::deleteById)
                        .andRoute(GET(""), userService::findAll)
                        .andRoute(GET("/{id}/find-by"), userService::findById)
                        .andRoute(DELETE("/{token}/delete-all"), userService::deleteAll)
        );
    }
}
