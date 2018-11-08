package cl.gestion.proyecto.service.validator.utils;

import cl.gestion.proyecto.service.validator.base.BaseValidation;
import cl.gestion.proyecto.service.validator.base.GenericValidation;

import java.util.Objects;

public class ValidationUtils {

    public static final BaseValidation<String> notNullString = GenericValidation.from(Objects::nonNull);
    public static final BaseValidation<String> notEmptyString = GenericValidation.from(s -> !s.isEmpty());
    public static final BaseValidation<Integer> notNullInteger = GenericValidation.from(Objects::nonNull);
    public static final BaseValidation<Integer> greaterThanZero = GenericValidation.from(s -> s > 0);
    public static final BaseValidation<Object> objectNotNull = GenericValidation.from(Objects::nonNull);

    public static BaseValidation<String> stringMoreThan(int size) {
        return GenericValidation.from(s -> ((String) s).length() > size);
    }

    public static BaseValidation<String> stringLessThan(int size) {
        return GenericValidation.from(s -> ((String) s).length() < size);
    }

    public static BaseValidation<String> stringBetween(int morethan, int lessThan) {
        return stringMoreThan(morethan).and(stringLessThan(lessThan));
    }

    public static BaseValidation<Integer> integerMoreThan(int limit) {
        return GenericValidation.from(s -> s > limit);
    }

    public static BaseValidation<Integer> integerLessThan(int size) {
        return GenericValidation.from(s -> s < size);
    }

    public static BaseValidation<Integer> integerBetween(int morethan, int lessThan) {
        return integerMoreThan(morethan).and(integerLessThan(lessThan));
    }
}
