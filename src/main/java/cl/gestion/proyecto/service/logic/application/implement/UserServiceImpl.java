package cl.gestion.proyecto.service.logic.application.implement;

import cl.gestion.proyecto.model.entities.application.UserEntity;
import cl.gestion.proyecto.model.request.application.UserRequest;
import cl.gestion.proyecto.repository.application.UserRepository;
import cl.gestion.proyecto.service.logic.application.UserService;
import cl.gestion.proyecto.service.logic.base.implement.BaseServiceImpl;
import cl.gestion.proyecto.utils.secure.JWTUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserEntity, String> implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository, final JWTUtils jwtUtils) {
        super(jwtUtils);
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> insert(final ServerRequest request) {
        try {
            return request.bodyToMono(UserRequest.class).map((result) -> {
                UserEntity entity = UserEntity.builder().build();
                BeanUtils.copyProperties(result, entity);
                entity.setEnabled(true);
                try {
                    this.generateAuditingEntity(entity.getAuditing(), request);
                } catch (Exception ex) {
                    return errorHandler(ex).toFuture().join();
                }
                return ServerResponse.ok().body(this.userRepository.insert(entity), UserEntity.class).toFuture().join();
            });
        } catch (Exception ex) {
            return this.errorHandler(ex);
        }
    }

    public Mono<ServerResponse> update(final ServerRequest request) {
        try {
            String id = request.pathVariable("id");
            UserEntity find = this.userRepository.findById(id).toFuture().get();
            if (find == null)
                return this.notFoundHandler("El usuario que se desea modificar no existe");

            return request.bodyToMono(UserRequest.class).map((result) -> {
                UserEntity entity = UserEntity.builder().build();
                BeanUtils.copyProperties(result, entity);
                entity.setEnabled(true);
                entity.set_id(id);
                try {
                    entity.setAuditing(this.generateAuditingEntity(find.getAuditing(), request).toFuture().get());
                } catch (Exception ex) {
                    return this.errorHandler(ex).toFuture().join();
                }
                return ServerResponse.ok().body(this.userRepository.save(entity), UserEntity.class).toFuture().join();
            });
        } catch (Exception ex) {
            return this.errorHandler(ex);
        }
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        try {
            String id = request.pathVariable("id");
            UserEntity entity = this.userRepository.findById(id).toFuture().get();
            if (entity == null)
                return this.notFoundHandler("El usuario que se desea modificar no existe");
            return this.userRepository.deleteById(id).map((result) ->
                    this.okHandler("El registro se a eliminado con exito").toFuture().join()
            );
        } catch (Exception ex) {
            return this.errorHandler(ex);
        }
    }

    public Mono<ServerResponse> deleteAll(ServerRequest request) {
        try {
            if (this.validateTokenRequest(request).toFuture().join()) {
                return this.userRepository.deleteAll().map(result ->
                        this.okHandler("Todos los registros han sido eliminados con exito").toFuture().join()
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
            return ServerResponse.ok().body(this.userRepository.findAll(), UserEntity.class);
        } catch (Exception ex) {
            return this.errorHandler(ex);
        }
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        try {
            String id = request.pathVariable("id");
            return ServerResponse.ok().body(this.userRepository.findById(id), UserEntity.class);
        } catch (Exception ex) {
            return this.errorHandler(ex);
        }
    }

}
