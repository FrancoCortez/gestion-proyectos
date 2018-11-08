package cl.gestion.proyecto.service.validator.domain.implement;

import cl.gestion.proyecto.model.request.domain.TypeUserRequest;
import cl.gestion.proyecto.service.validator.base.BaseValidatorImpl;
import cl.gestion.proyecto.service.validator.domain.TypeUserValidator;
import cl.gestion.proyecto.service.validator.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class TypeUserValidatorImpl extends BaseValidatorImpl implements TypeUserValidator {

    public Mono<Void> validateRequestTypeUser(TypeUserRequest request) throws Exception {
        log.info("Init validation type user request");
        StringBuilder errors = new StringBuilder();
        errors.append(
                ValidationUtils.notNullString
                        .and(ValidationUtils.notEmptyString)
                        .genericResult(request.getName())
                        .getFieldNameIfInvalid("Error el nombre del tipo de usuario no puede ser null ni vacio")
                        .orElse("")
        );

        log.info("End execution validation");
        if (!errors.toString().isEmpty()) {
            log.info("Error in the validation.");
            log.info(errors.toString());
            throw new Exception(errors.toString());
        }
        return Mono.empty();
    }

}
