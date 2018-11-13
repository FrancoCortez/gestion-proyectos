package cl.gestion.proyecto.service.logic.base.implement;

import cl.gestion.proyecto.model.entities.base.AuditingEntity;
import cl.gestion.proyecto.model.entities.base.BaseEntity;
import cl.gestion.proyecto.model.utils.ErrorHandlerResponse;
import cl.gestion.proyecto.model.utils.SuccessHandlerResponse;
import cl.gestion.proyecto.service.logic.base.BaseService;
import cl.gestion.proyecto.utils.secure.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Date;

@Slf4j
public class BaseServiceImpl<T extends BaseEntity, ID extends String> implements BaseService<T, ID> {

    private final JWTUtils jwtUtils;

    protected BaseServiceImpl(final JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    protected Mono<ServerResponse> errorHandler(Exception ex) {
        ErrorHandlerResponse response = ErrorHandlerResponse.builder()
                .msg(ex.getMessage())
                .trace(ex.getStackTrace())
                .cause(ex.getCause())
                .build();
        return ServerResponse.badRequest().body(Mono.just(response), ErrorHandlerResponse.class);
    }

    protected Mono<ServerResponse> errorHandler(String msg) {
        ErrorHandlerResponse response = ErrorHandlerResponse.builder()
                .msg(msg)
                .build();
        return ServerResponse.badRequest().body(Mono.just(response), ErrorHandlerResponse.class);
    }

    protected Mono<ServerResponse> warnnigHandler(String msg) {
        ErrorHandlerResponse response = ErrorHandlerResponse.builder()
                .msg(msg)
                .build();
        return ServerResponse.status(409).body(Mono.just(response), ErrorHandlerResponse.class);
    }

    protected Mono<ServerResponse> notFoundHandler(String msg) {
        ErrorHandlerResponse response = ErrorHandlerResponse.builder()
                .msg(msg)
                .build();
        return ServerResponse.status(404).body(Mono.just(response), ErrorHandlerResponse.class);
    }

    protected Mono<ServerResponse> okHandler(Object data) {
        SuccessHandlerResponse response = SuccessHandlerResponse.builder()
                .data(data)
                .build();
        return ServerResponse.ok().body(Mono.just(response), SuccessHandlerResponse.class);
    }

    protected Mono<Boolean> validateTokenRequest(ServerRequest request) throws Exception {
        String tokenRequest = request.pathVariable("token");
        String tokenHeader = request.headers().header(HttpHeaders.AUTHORIZATION).get(0);
        if (tokenHeader == null) {
            throw new Exception("El token de autorizacion es invalido");
        }
        if (tokenHeader.startsWith("Bearer ")) {
            tokenHeader = tokenHeader.substring(7);
        } else {
            throw new Exception("Error token invalido para realizar la operacion.");
        }
        return Mono.justOrEmpty(tokenHeader.equals(tokenRequest));
    }

    private Mono<String> obtainDataToken(ServerRequest request) throws Exception {
        String tokenHeader = request.headers().header(HttpHeaders.AUTHORIZATION).get(0);
        if (tokenHeader == null) {
            throw new Exception("El token de autorizacion es invalido");
        }
        if (tokenHeader.startsWith("Bearer ")) {
            tokenHeader = tokenHeader.substring(7);
        } else {
            throw new Exception("Error token invalido para realizar la operacion.");
        }

        String username = jwtUtils.getUsernameFromToken(tokenHeader);
        return Mono.justOrEmpty(username);
    }

    protected Mono<AuditingEntity> generateAuditingEntity(AuditingEntity auditingEntity, ServerRequest request) throws Exception {
        if (auditingEntity == null) {
            log.info("Generate auditing the origin null");
            return Mono.justOrEmpty(AuditingEntity.builder()
                    .createdBy(obtainDataToken(request).toFuture().get())
                    .createdDate(new Date())
                    .delete(true)
                    .lastModifiedDate(new Date())
                    .lastModifiedBy(obtainDataToken(request).toFuture().get())
                    .version(1L)
                    .build());
        } else {
            log.info("Generate auditing the origin != null");
            auditingEntity.setLastModifiedBy(obtainDataToken(request).toFuture().get());
            auditingEntity.setLastModifiedDate(new Date());
            auditingEntity.setVersion(auditingEntity.getVersion() + 1);
            return Mono.justOrEmpty(auditingEntity);
        }
    }
}
