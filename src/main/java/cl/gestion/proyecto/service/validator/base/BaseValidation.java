package cl.gestion.proyecto.service.validator.base;

@FunctionalInterface
public interface BaseValidation<K> {

    GenericResult genericResult(K param);

    default BaseValidation<K> and(BaseValidation<K> otro) {
        return (param) -> {
            GenericResult resultado = this.genericResult(param);
            return !resultado.isValid() ? resultado : otro.genericResult(param);
        };
    }

    default BaseValidation<K> or(BaseValidation<K> otro) {
        return (param) -> {
            GenericResult result = this.genericResult(param);
            return result.isValid() ? result : otro.genericResult(param);
        };
    }
}
