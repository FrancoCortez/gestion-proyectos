package cl.gestion.proyecto.service.validator.application.implement;

import cl.gestion.proyecto.model.request.application.ProyectRequest;
import cl.gestion.proyecto.service.validator.application.ProyectValidator;
import cl.gestion.proyecto.service.validator.base.BaseValidatorImpl;
import cl.gestion.proyecto.service.validator.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ProyectValidatorImpl extends BaseValidatorImpl implements ProyectValidator {

    public Mono<Void> validateRequestProyectI(final ProyectRequest request) throws Exception {
        StringBuilder errors = new StringBuilder();
        errors.append(
                ValidationUtils.notNullString
                        .and(ValidationUtils.notEmptyString)
                        .genericResult(request.getName())
                        .getFieldNameIfInvalid("El nombre del proyecto no puede ser null")
                        .orElse("")
        );
        errors.append(
                ValidationUtils.notNullString
                        .and(ValidationUtils.notEmptyString)
                        .genericResult(request.getClientId())
                        .getFieldNameIfInvalid("El proyecto debe tener un cliente asignado")
                        .orElse("")
        );
        errors.append(
                ValidationUtils.notNullString
                        .and(ValidationUtils.notEmptyString)
                        .genericResult(request.getProyectStateId())
                        .getFieldNameIfInvalid("El proyecto debe tener un estado por default")
                        .orElse("")
        );
        errors.append(
                ValidationUtils.notNullObject
                        .genericResult(request.getInit())
                        .getFieldNameIfInvalid("El proyecto debe tener una fecha de inicio")
                        .orElse("")
        );
        errors.append(
                ValidationUtils.notNullSetList
                        .and(ValidationUtils.notEmptySetList)
                        .genericResult(request.getProyectBoos())
                        .getFieldNameIfInvalid("El proyecto debe tener un jefe de proyecto")
                        .orElse("")
        );
        errors.append(
                ValidationUtils.notNullSetList
                        .and(ValidationUtils.notEmptySetList)
                        .genericResult(request.getComercialManager())
                        .getFieldNameIfInvalid("El proyecto debe tener un gerente comercial")
                        .orElse("")
        );

        log.info("Resultado de la validacion : " + errors.toString());
        if (!errors.toString().isEmpty()) {
            throw new Exception(errors.toString());
        }
        return Mono.empty();
    }


}
