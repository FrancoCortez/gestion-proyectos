package cl.gestion.proyecto.service.validator.base;

import java.util.function.Predicate;

public class GenericValidation<K> implements BaseValidation<K> {
    private Predicate<K> predicate;

    private GenericValidation(Predicate<K> predicate) {
        this.predicate = predicate;
    }

    public static <K> GenericValidation<K> from(Predicate<K> predicate) {
        return new GenericValidation<>(predicate);
    }

    @Override
    public GenericResult genericResult(K param) {
        return predicate.test(param) ? GenericResult.ok() : GenericResult.fail();
    }

}
