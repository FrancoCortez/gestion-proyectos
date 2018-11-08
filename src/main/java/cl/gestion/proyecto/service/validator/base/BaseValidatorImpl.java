package cl.gestion.proyecto.service.validator.base;

import cl.gestion.proyecto.service.validator.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class BaseValidatorImpl implements BaseValidator {

    public Mono<Void> validateId(String id) throws Exception {
        log.info("Init validation id");
        StringBuilder errors = new StringBuilder();
        errors.append(
                ValidationUtils.notNullString
                        .and(ValidationUtils.notEmptyString)
                        .genericResult(id)
                        .getFieldNameIfInvalid("Error el id es null o esta vacio")
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
