package cl.gestion.proyecto.service.validator.base;

import reactor.core.publisher.Mono;

public interface BaseValidator {

    Mono<Void> validateId(String id) throws Exception;
}
