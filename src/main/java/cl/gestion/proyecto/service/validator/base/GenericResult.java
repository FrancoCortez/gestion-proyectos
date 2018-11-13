package cl.gestion.proyecto.service.validator.base;

import java.util.Optional;

public class GenericResult {
    private final boolean isValid;


    private GenericResult(boolean isValid) {
        this.isValid = isValid;
    }

    static GenericResult ok() {
        return new GenericResult(true);
    }

    static GenericResult fail() {
        return new GenericResult(false);
    }

    boolean isValid() {
        return isValid;
    }

    public Optional<String> getFieldNameIfInvalid(String field) {
        return this.isValid ? Optional.empty() : Optional.of(field + " " + System.lineSeparator() + " ");
    }
}
