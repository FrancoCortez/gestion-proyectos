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
            return request.bodyToMono(TypeUserRequest.class).map((result) -> {
                try {
                    this.typeUserValidator.validateRequestTypeUser(result);
                } catch (Exception ex) {
                    log.error("Error ex: " + ex.getMessage());
                    return this.errorHandler(ex).toFuture().join();
                }
                log.info("Object request : " + result.toString());
                TypeUserEntity entity = TypeUserEntity.builder().build();
                BeanUtils.copyProperties(result, entity);
                try {
                    log.info("Generate Auditing");
                    entity.setAuditing(this.generateAuditingEntity(entity.getAuditing(), request).toFuture().get());
                    log.info("Auditing create: " + entity.getAuditing().toString());

                } catch (Exception ex) {
                    log.error("Error ex: " + ex.getMessage());
                    return errorHandler(ex).toFuture().join();
                }
                log.info("Object final to insert : " + entity.toString());
                log.info("End Execution");
                return ServerResponse.ok().body(this.typeUserRepository.insert(entity), TypeUserEntity.class).toFuture().join();
            });
        } catch (Exception ex) {
            log.error("Error ex: " + ex.getMessage());
            return this.errorHandler(ex);
        }
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        try {
            String id = request.pathVariable("id");
            this.typeUserValidator.validateId(id).toFuture().get();

            TypeUserEntity find = this.typeUserRepository.findById(id).toFuture().get();
            if (find == null)
                return this.notFoundHandler("El tipo de usuario que se desea modificar no se encuentra");

            return request.bodyToMono(TypeUserRequest.class).map(result -> {
                try {
                    this.typeUserValidator.validateRequestTypeUser(result);
                } catch (Exception ex) {
                    log.error("Error  ex: " + ex.getMessage());
                    return this.errorHandler(ex).toFuture().join();
                }
                TypeUserEntity entity = TypeUserEntity.builder().build();
                BeanUtils.copyProperties(result, entity);
                entity.set_id(id);
                try {
                    entity.setAuditing(this.generateAuditingEntity(find.getAuditing(), request).toFuture().get());
                } catch (Exception ex) {
                    return this.errorHandler(ex).toFuture().join();
                }
                return ServerResponse.ok().body(this.typeUserRepository.save(entity), TypeUserEntity.class).toFuture().join();
            });

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
            return this.typeUserRepository.deleteById(id).map(result ->
                    this.okHandler("El registro fue eliminado con exito").toFuture().join()
            );

        } catch (Exception ex) {
            log.error("Error ex : " + ex.getMessage());
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
}
