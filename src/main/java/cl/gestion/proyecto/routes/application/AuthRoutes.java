package cl.gestion.proyecto.routes.application;

import cl.gestion.proyecto.routes.base.BaseRouter;
import cl.gestion.proyecto.service.logic.auth.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AuthRoutes extends BaseRouter {
    private String baseRouterPath = this.baseRouter + "auth";

    @Bean(value = "auth-router")
    public RouterFunction<ServerResponse> router(AuthService authService) {
        return RouterFunctions.nest(path(baseRouterPath),
                route(POST("/login"), authService::login)
                        .andRoute(POST("/register"), authService::register)
        );
    }
}
