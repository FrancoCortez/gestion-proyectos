package cl.gestion.proyecto.service.validator.application;

import cl.gestion.proyecto.model.request.application.UserRequest;
import cl.gestion.proyecto.model.request.application.UserUpdateRequest;
import cl.gestion.proyecto.service.validator.base.BaseValidator;
import reactor.core.publisher.Mono;

public interface UserValidator extends BaseValidator {
    Mono<Void> validateRequestUser(UserRequest request) throws Exception;

    Mono<Void> validateRequestUpdateUser(UserUpdateRequest request) throws Exception;
}
