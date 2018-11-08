package cl.gestion.proyecto.service.validator.domain;

import cl.gestion.proyecto.model.request.domain.TypeUserRequest;
import cl.gestion.proyecto.service.validator.base.BaseValidator;
import reactor.core.publisher.Mono;

public interface TypeUserValidator extends BaseValidator {
    Mono<Void> validateRequestTypeUser(TypeUserRequest request) throws Exception;
}
