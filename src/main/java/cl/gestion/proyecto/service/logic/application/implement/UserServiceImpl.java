package cl.gestion.proyecto.service.logic.application.implement;

import cl.gestion.proyecto.model.entities.application.UserEntity;
import cl.gestion.proyecto.model.request.application.UserRequest;
import cl.gestion.proyecto.model.request.application.UserUpdateRequest;
import cl.gestion.proyecto.model.response.application.UserFullResponse;
import cl.gestion.proyecto.model.response.domain.TypeUserFullResponse;
import cl.gestion.proyecto.repository.application.UserRepository;
import cl.gestion.proyecto.repository.domain.TypeUserRepository;
import cl.gestion.proyecto.service.logic.application.UserService;
import cl.gestion.proyecto.service.logic.base.implement.BaseServiceImpl;
import cl.gestion.proyecto.service.validator.application.UserValidator;
import cl.gestion.proyecto.utils.secure.JWTUtils;
import cl.gestion.proyecto.utils.secure.PBKDF2Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<UserEntity, String> implements UserService {

    private final UserRepository userRepository;
    private final PBKDF2Encoder pbkdf2Encoder;
    private final UserValidator userValidator;
    private final TypeUserRepository typeUserRepository;

    public UserServiceImpl(final UserRepository userRepository, final JWTUtils jwtUtils, final PBKDF2Encoder pbkdf2Encoder, final UserValidator userValidator, TypeUserRepository typeUserRepository) {
        super(jwtUtils);
        this.userRepository = userRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
        this.userValidator = userValidator;
        this.typeUserRepository = typeUserRepository;
    }

    public Mono<ServerResponse> insert(final ServerRequest request) {
        log.info("Init the insert user");
        try {
            return request.bodyToMono(UserRequest.class).map((result) -> {
                try {
                    this.userValidator.validateRequestUser(result);
                } catch (Exception ex) {
                    return this.errorHandler(ex).toFuture().join();
                }
                log.info("Description request user : " + result.toString());
                log.info("Create user Entity");
                UserEntity entity = UserEntity.builder().build();
                BeanUtils.copyProperties(result, entity);
                entity.setEnabled(true);
                try {
                    log.info("Generate Auditing");
                    entity.setAuditing(this.generateAuditingEntity(entity.getAuditing(), request).toFuture().get());
                    log.info("Auditing create: " + entity.getAuditing().toString());
                } catch (Exception ex) {
                    log.error("Error the generate auditing : ex = " + ex.getMessage());
                    return errorHandler(ex).toFuture().join();
                }
                entity.setPassword(this.pbkdf2Encoder.encode(entity.getPassword()));
                log.info("End Execution");
                return ServerResponse.ok().body(this.userRepository.insert(entity), UserEntity.class).toFuture().join();
            });
        } catch (Exception ex) {
            log.error("Error the generate insert service : ex = " + ex.getMessage());
            return this.errorHandler(ex);
        }
    }

    public Mono<ServerResponse> update(final ServerRequest request) {
        log.info("Init the update user");
        try {
            String id = request.pathVariable("id");
            this.userValidator.validateId(id).toFuture().join();
            UserEntity find = this.userRepository.findById(id).toFuture().get();
            if (find == null) {
                log.info("El usuario " + id + " no existe");
                return this.notFoundHandler("El usuario que se desea modificar no existe");
            }
            log.info("Entity end to search modify: " + find.toString());
            return request.bodyToMono(UserUpdateRequest.class).map((result) -> {
                try {
                    this.userValidator.validateRequestUpdateUser(result);
                } catch (Exception ex) {
                    return this.errorHandler(ex).toFuture().join();
                }
                log.info("Description request user : " + result.toString());
                log.info("Update user Entity");
                UserEntity entity = UserEntity.builder().build();
                BeanUtils.copyProperties(result, entity);
                entity.setEnabled(true);
                entity.set_id(id);
                try {
                    log.info("Generate Auditing");
                    entity.setAuditing(this.generateAuditingEntity(find.getAuditing(), request).toFuture().get());
                    log.info("Auditing create: " + entity.getAuditing().toString());
                } catch (Exception ex) {
                    log.error("Error the generate auditing : ex = " + ex.getMessage());
                    return this.errorHandler(ex).toFuture().join();
                }
                entity.setPassword(find.getPassword());
                return ServerResponse.ok().body(this.userRepository.save(entity), UserEntity.class).toFuture().join();
            });
        } catch (Exception ex) {
            log.error("Error ex: " + ex.getMessage());
            return this.errorHandler(ex);
        }
    }

    public Mono<ServerResponse> deleteById(final ServerRequest request) {
        log.info("Init the delete by id user");
        try {
            String id = request.pathVariable("id");
            UserEntity entity = this.userRepository.findById(id).toFuture().get();
            if (entity == null) {
                log.info("El usuario " + id + " no existe");
                return this.notFoundHandler("El usuario que se desea modificar no existe");
            }
            log.info("Entity end to search modify: " + entity.toString());
            return this.userRepository.deleteById(id).map((result) ->
                    this.okHandler("El registro se a eliminado con exito").toFuture().join()
            );
        } catch (Exception ex) {
            log.error("Error ex: " + ex.getMessage());
            return this.errorHandler(ex);
        }
    }

    public Mono<ServerResponse> deleteAll(final ServerRequest request) {
        log.info("Init the delete all user");
        try {
            if (this.validateTokenRequest(request).toFuture().join()) {
                return this.userRepository.deleteAll().map(result ->
                        this.okHandler("Todos los registros han sido eliminados con exito").toFuture().join()
                );
            } else {
                log.info("Token request is invalid with the auth token");
                return ServerResponse.badRequest().build();
            }
        } catch (Exception ex) {
            log.error("Error ex: " + ex.getMessage());
            return this.errorHandler(ex);
        }
    }

    public Mono<ServerResponse> findAll(final ServerRequest request) {
        log.info("Init the find all user");
        try {
            Flux<UserFullResponse> fulluser = this.userRepository.findAll().flatMap(result -> {
                log.info("Init search type user for id");
                log.info("User to search object : " + result.toString());
                return this.typeUserRepository.findById(result.getTypeUserId()).flatMap(resp -> {
                    log.info("Type user object : " + resp.toString());
                    UserFullResponse userFullResponse = UserFullResponse.builder().build();
                    BeanUtils.copyProperties(result, userFullResponse);
                    TypeUserFullResponse typeUserFullResponse = TypeUserFullResponse.builder().build();
                    BeanUtils.copyProperties(resp, typeUserFullResponse);
                    userFullResponse.setTypeUser(typeUserFullResponse);
                    log.info("Set type user in the user :" + userFullResponse.toString());
                    return Mono.justOrEmpty(userFullResponse);
                });
            });
            log.info("END the find all user");
            return ServerResponse.ok().body(fulluser, UserFullResponse.class);
        } catch (Exception ex) {
            log.error("Error ex: " + ex.getMessage());
            return this.errorHandler(ex);
        }
    }


    public Mono<ServerResponse> findById(final ServerRequest request) {
        log.info("Init the find by id user");
        try {
            String id = request.pathVariable("id");
            this.userValidator.validateId(id).toFuture().join();
            log.info("User search the id : " + id);
            return ServerResponse.ok().body(this.userRepository.findById(id), UserEntity.class);
        } catch (Exception ex) {
            log.error("Error ex: " + ex.getMessage());
            return this.errorHandler(ex);
        }
    }

}
