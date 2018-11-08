package cl.gestion.proyecto.service.logic.domain.implement;

import cl.gestion.proyecto.model.entities.domain.TypeUserEntity;
import cl.gestion.proyecto.model.request.domain.TypeUserRequest;
import cl.gestion.proyecto.repository.domain.TypeUserRepository;
import cl.gestion.proyecto.service.logic.base.implement.BaseServiceImpl;
import cl.gestion.proyecto.service.logic.domain.TypeUserService;
import cl.gestion.proyecto.utils.secure.JWTUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class TypeUserServiceImpl extends BaseServiceImpl<TypeUserEntity, String> implements TypeUserService {

    private final TypeUserRepository typeUserRepository;

    public TypeUserServiceImpl(final TypeUserRepository typeUserRepository, final JWTUtils jwtUtils) {
        super(jwtUtils);
        this.typeUserRepository = typeUserRepository;
    }

    public Mono<ServerResponse> insert(ServerRequest request) {
        try {
            return request.bodyToMono(TypeUserRequest.class).map((result) -> {
                TypeUserEntity entity = TypeUserEntity.builder().build();
                BeanUtils.copyProperties(result, entity);
                return ServerResponse.ok().body(this.typeUserRepository.insert(entity), TypeUserEntity.class).toFuture().join();
            });
        } catch (Exception ex) {
            return this.errorHandler(ex);
        }
    }
}
