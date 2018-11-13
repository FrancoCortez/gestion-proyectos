package cl.gestion.proyecto.service.validator.application.implement;

import cl.gestion.proyecto.model.request.application.UserRequest;
import cl.gestion.proyecto.model.request.application.UserUpdateRequest;
import cl.gestion.proyecto.service.validator.application.UserValidator;
import cl.gestion.proyecto.service.validator.base.BaseValidatorImpl;
import cl.gestion.proyecto.service.validator.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class UserValidatorImpl extends BaseValidatorImpl implements UserValidator {

    public Mono<Void> validateRequestUser(UserRequest request) throws Exception {
        log.info("Init validation user request");
        StringBuilder errors = new StringBuilder();
        errors.append(
                ValidationUtils.notNullString
                        .and(ValidationUtils.notEmptyString)
                        .genericResult(request.getUsername())
                        .getFieldNameIfInvalid("Error el nombre de usuario no puede ser null o vacio")
                        .orElse("")
        );
        errors.append(
                ValidationUtils.notNullString
                        .and(ValidationUtils.notEmptyString)
                        .genericResult(request.getPassword())
                        .getFieldNameIfInvalid("Error la contraceña no puede ser null o estar vacia")
                        .orElse("")
        );
        errors.append(
                ValidationUtils.notNullString
                        .and(ValidationUtils.notEmptyString)
                        .genericResult(request.getMail())
                        .getFieldNameIfInvalid("El correo no puede ser null o estar vacio")
                        .orElse("")
        );
        errors.append(
                ValidationUtils.notNullObject
                        .genericResult(request.getPersonalData())
                        .getFieldNameIfInvalid("Los datos personales se encuentran nulos")
                        .orElse("")
        );

        if (request.getPersonalData() != null) {
            errors.append(
                    ValidationUtils.notNullString
                            .and(ValidationUtils.notEmptyString)
                            .genericResult(request.getPersonalData().getAddress())
                            .getFieldNameIfInvalid("La direccion de la persona no puede ser null o estar vacia")
                            .orElse("")
            );
            errors.append(
                    ValidationUtils.notNullString
                            .and(ValidationUtils.notEmptyString)
                            .genericResult(request.getPersonalData().getName())
                            .getFieldNameIfInvalid("El nombre de la persona no puede ser null o estar vacia")
                            .orElse("")
            );
            errors.append(
                    ValidationUtils.notNullString
                            .and(ValidationUtils.notEmptyString)
                            .genericResult(request.getPersonalData().getLastName())
                            .getFieldNameIfInvalid("El apellido del usuario no puede ser null o estar vacio")
                            .orElse("")
            );

            errors.append(
                    ValidationUtils.notNullString
                            .and(ValidationUtils.notEmptyString)
                            .genericResult(request.getPersonalData().getRut())
                            .getFieldNameIfInvalid("El rut no puede ser null o estar vacio")
                            .orElse("")
            );
        }

        log.info("End execution validation");
        if (!errors.toString().isEmpty()) {
            log.info("Error in the validation.");
            log.info(errors.toString());
            throw new Exception(errors.toString());
        }
        return Mono.empty();
    }

    public Mono<Void> validateRequestUpdateUser(UserUpdateRequest request) throws Exception {
        log.info("Init validation user request update");
        StringBuilder errors = new StringBuilder();
        errors.append(
                ValidationUtils.notNullString
                        .and(ValidationUtils.notEmptyString)
                        .genericResult(request.getUsername())
                        .getFieldNameIfInvalid("Error el nombre de usuario no puede ser null o vacio")
                        .orElse("")
        );
        errors.append(
                ValidationUtils.notNullString
                        .and(ValidationUtils.notEmptyString)
                        .genericResult(request.getMail())
                        .getFieldNameIfInvalid("El correo no puede ser null o estar vacio")
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
