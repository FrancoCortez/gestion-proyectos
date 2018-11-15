package cl.gestion.proyecto.service.logic.auth.implement;

import cl.gestion.proyecto.model.entities.application.RoleEntity;
import cl.gestion.proyecto.model.entities.application.UserEntity;
import cl.gestion.proyecto.model.entities.base.AuditingEntity;
import cl.gestion.proyecto.model.request.application.LoginRequest;
import cl.gestion.proyecto.model.request.application.RegisterRequest;
import cl.gestion.proyecto.model.utils.BaseResponse;
import cl.gestion.proyecto.model.utils.HandlerResponse;
import cl.gestion.proyecto.repository.application.UserRepository;
import cl.gestion.proyecto.service.logic.auth.AuthService;
import cl.gestion.proyecto.service.logic.base.implement.BaseServiceImpl;
import cl.gestion.proyecto.utils.secure.JWTUtils;
import cl.gestion.proyecto.utils.secure.PBKDF2Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Date;

@Service
@Slf4j
public class AuthServiceImpl extends BaseServiceImpl<UserEntity, String> implements AuthService {

    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;
    private final PBKDF2Encoder passwordEncoder;

    public AuthServiceImpl(final UserRepository userRepository, final JWTUtils jwtUtils, final PBKDF2Encoder passwordEncoder) {
        super(jwtUtils);
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<HandlerResponse<String>> login2(LoginRequest loginRequest) {
        return this.userRepository.findByUsername(loginRequest.getUsername())
                .flatMap(result -> {
                    log.info("Validate login with password");
                    log.info("user: " + result.toString());
                    if (passwordEncoder.encode(loginRequest.getPassword()).equals(result.getPassword())) {
                        log.info("Validation ok");
                        HandlerResponse<String> handlerResponse = HandlerResponse.<String>builder()
                                .status(200)
                                .cla(String.class)
                                .data(Mono.justOrEmpty(this.jwtUtils.generateToken(result)))
                                .build();
                        return Mono.justOrEmpty(handlerResponse);
                    }
                    log.info("Error en las credenciales");
                    return Mono.justOrEmpty(HandlerResponse
                            .<String>builder()
                            .status(403)
                            .cla(String.class)
                            .data(Mono.justOrEmpty("No se encuentra autorizado. Credenciales invalidas."))
                            .build());
                });
    }

    public Mono<HandlerResponse<BaseResponse>> register2(RegisterRequest request) {
        try {
            UserEntity entity = UserEntity.builder().build();
            BeanUtils.copyProperties(request, entity);
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            entity.setEnabled(true);
            entity.setRoles(Collections.singletonList(RoleEntity.ROLE_USER));
            entity.setAuditing(AuditingEntity.builder()
                    .createdBy("LEGACY")
                    .createdDate(new Date())
                    .delete(true)
                    .lastModifiedDate(new Date())
                    .lastModifiedBy("LEGACY")
                    .version(1L)
                    .build());
            return this.userRepository.insert(entity).flatMap(response -> {
                return Mono.justOrEmpty(HandlerResponse.<BaseResponse>builder()
                        .status(200)
                        .data(Mono.just(BaseResponse.builder().data(entity).build()))
                        .cla(BaseResponse.class)
                        .build());
            });
        } catch (Exception ex) {
            return Mono.justOrEmpty(HandlerResponse.<BaseResponse>builder()
                    .status(400)
                    .data(Mono.justOrEmpty(BaseResponse.builder().data(ex.getMessage()).build()))
                    .cla(BaseResponse.class)
                    .build());
        }
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        log.info("Init login");
        try {
            return request.bodyToMono(LoginRequest.class).map((result) -> {
                Mono<ServerResponse> serverResponse = this.userRepository.findByUsername(result.getUsername()).map((find) -> {
                    log.info("Validate login with password");
                    if (passwordEncoder.encode(result.getPassword()).equals(find.getPassword())) {
                        log.info("Validation ok");
                        return ServerResponse.ok().header("Authorization", this.jwtUtils.generateToken(find)).build().toFuture().join();
                    }
                    log.info("Validation error");
                    return ServerResponse.status(403).build().toFuture().join();
                });
                log.info("End login");
                return serverResponse.toFuture().join();
            });
        } catch (Exception ex) {
            return this.errorHandler(ex);
        }
    }

    public Mono<ServerResponse> register(ServerRequest request) {
        try {
            return request.bodyToMono(RegisterRequest.class).map((result) -> {
                UserEntity entity = UserEntity.builder().build();
                BeanUtils.copyProperties(result, entity);
                entity.setPassword(passwordEncoder.encode(entity.getPassword()));
                entity.setEnabled(true);
                entity.setRoles(Collections.singletonList(RoleEntity.ROLE_USER));
                entity.setAuditing(AuditingEntity.builder()
                        .createdBy("LEGACY")
                        .createdDate(new Date())
                        .delete(true)
                        .lastModifiedDate(new Date())
                        .lastModifiedBy("LEGACY")
                        .version(1L)
                        .build());
                return ServerResponse.ok().body(this.userRepository.insert(entity), UserEntity.class).toFuture().join();
            });
        } catch (Exception ex) {
            return this.errorHandler(ex);
        }
    }
}
