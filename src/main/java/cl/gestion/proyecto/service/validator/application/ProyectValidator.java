package cl.gestion.proyecto.service.validator.application;

import cl.gestion.proyecto.model.request.application.ProyectRequest;
import cl.gestion.proyecto.service.validator.base.BaseValidator;
import reactor.core.publisher.Mono;

public interface ProyectValidator extends BaseValidator {

    Mono<Void> validateRequestProyectI(final ProyectRequest request) throws Exception;
}
