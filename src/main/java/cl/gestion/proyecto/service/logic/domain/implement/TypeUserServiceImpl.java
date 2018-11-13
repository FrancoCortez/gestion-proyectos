package cl.gestion.proyecto.service.logic.domain.implement;

import cl.gestion.proyecto.model.entities.domain.TypeUserEntity;
import cl.gestion.proyecto.model.request.domain.TypeUserRequest;
import cl.gestion.proyecto.repository.domain.TypeUserRepository;
import cl.gestion.proyecto.service.logic.base.implement.BaseServiceImpl;
import cl.gestion.proyecto.service.logic.domain.TypeUserService;
import cl.gestion.proyecto.service.validator.domain.TypeUserValidator;
import cl.gestion.proyecto.utils.secure.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class TypeUserServiceImpl extends BaseServiceImpl<TypeUserEntity, String> implements TypeUserService {

    private final TypeUserRepository typeUserRepository;
    private final TypeUserValidator typeUserValidator;

    public TypeUserServiceImpl(final TypeUserRepository typeUserRepository, final JWTUtils jwtUtils, final TypeUserValidator typeUserValidator) {
        super(jwtUtils);
        this.typeUserRepository = typeUserRepository;
        this.typeUserValidator = typeUserValidator;
    }

    public Mono<ServerResponse> insert(ServerRequest request) {
        log.info("Init insert type user");
        try {
            return request.bodyToMono(TypeUserRequest.class).flatMap((result) -> {
                try {
                    this.typeUserValidator.validateRequestTypeUser(result);
                } catch (Exception ex) {
                    log.error("Error ex: " + ex.getMessage());
                    return this.errorHandler(ex);
                }
                log.info("Object request : " + result.toString());
                TypeUserEntity entity = TypeUserEntity.builder().build();
                BeanUtils.copyProperties(result, entity);
                try {
                    log.info("Generate Auditing");
                    return this.generateAuditingEntity(entity.getAuditing(), request).flatMap(auditing -> {
                        entity.setAuditing(auditing);
                        log.info("Auditing create: " + entity.getAuditing().toString());
                        return this.typeUserRepository.insert(entity).flatMap(resp -> {
                            log.info("Entity insert: " + entity.toString());
                            return ServerResponse.ok().body(Mono.just(resp), TypeUserEntity.class);
                        });
                    });
                } catch (Exception ex) {
                    log.error("Error ex: " + ex.getMessage());
                    return errorHandler(ex);
                }
            });
        } catch (Exception ex) {
            log.error("Error ex: " + ex.getMessage());
            return this.errorHandler(ex);
        }
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        log.info("Init update type user");
        try {
            String id = request.pathVariable("id");
            this.typeUserValidator.validateId(id).toFuture().get();
            log.info("The id for update : " + id);
            return this.typeUserRepository.findById(id).flatMap(find -> {
                log.info("Entre al primer return");
                return request.bodyToMono(TypeUserRequest.class).flatMap(resp -> {
                    log.info("Entre al segundo return");
                    try {
                        this.typeUserValidator.validateRequestTypeUser(resp);
                    } catch (Exception ex) {
                        return this.errorHandler(ex);
                    }
                    TypeUserEntity entity = TypeUserEntity.builder().build();
                    BeanUtils.copyProperties(resp, entity);
                    entity.set_id(id);
                    try {
                        return this.generateAuditingEntity(entity.getAuditing(), request).flatMap(auditingEntity -> {
                            log.info("Entre al 3 return ");
                            entity.setAuditing(auditingEntity);
                            log.info("Entre al 4 return ");
                            return this.typeUserRepository.save(entity).flatMap(response ->
                                    ServerResponse.ok().body(Mono.justOrEmpty(response), TypeUserEntity.class)
                            );
                        });
                    } catch (Exception ex) {
                        return this.errorHandler(ex);
                    }
                });
            }).onErrorResume(error -> this.errorHandler(error.getMessage()));
        } catch (Exception ex) {
            log.error("Error ex: " + ex.getMessage());
            return this.errorHandler(ex);
        }
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        log.info("Init method delete by id");
        try {
            String id = request.pathVariable("id");
            this.typeUserValidator.validateId(id).toFuture().get();
            TypeUserEntity find = this.typeUserRepository.findById(id).toFuture().get();
            log.info("Search type user with id : " + id);
            if (find == null)
                return this.notFoundHandler("El tipo de usuario que se desea eliminar no se encuentra.");
            log.info("Object the delete : " + find.toString());
            log.info("End delete by id");
            return this.typeUserRepository.deleteById(id).map(result ->
                    this.okHandler("El registro fue eliminado con exito").toFuture().join()
            );

        } catch (Exception ex) {
            log.error("Error ex : " + ex.getMessage());
            return this.errorHandler(ex);
        }
    }

    public Mono<ServerResponse> deleteAll(ServerRequest request) {
        try {
            if (this.validateTokenRequest(request).toFuture().join()) {
                return this.typeUserRepository.deleteAll().map(result ->
                        this.okHandler("Todos los tipos de usuario han sido eliminados con exito").toFuture().join()
                );
            } else {
                return ServerResponse.badRequest().build();
            }
        } catch (Exception ex) {
            return this.errorHandler(ex);
        }
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        try {
            return ServerResponse.ok().body(this.typeUserRepository.findAll(), TypeUserEntity.class);
        } catch (Exception ex) {
            log.error("Error ex : " + ex.getMessage());
            return this.errorHandler(ex);
        }
    }


    public Mono<ServerResponse> findById(ServerRequest request) {
        log.info("Init find By id");
        try {
            String id = request.pathVariable("id");
            this.typeUserValidator.validateId(id).toFuture().join();
            log.info("Search type user for id : " + id);
            log.info("End Search by id");
            return ServerResponse.ok().body(this.typeUserRepository.findById(id), TypeUserEntity.class);
        } catch (Exception ex) {
            log.error("Error ex: " + ex.getMessage());
            return this.errorHandler(ex);
        }
    }

    public Mono<ServerResponse> findByName(ServerRequest request) {
        log.info("Init find By name");
        try {
            String name = request.pathVariable("name");
            this.typeUserValidator.validateId(name).toFuture().join();
            log.info("Search type user for name : " + name);
            log.info("End Search by name");
            return ServerResponse.ok().body(this.typeUserRepository.findByName(name), TypeUserEntity.class);
        } catch (Exception ex) {
            log.error("Error ex: " + ex.getMessage());
            return this.errorHandler(ex);
        }
    }

}
