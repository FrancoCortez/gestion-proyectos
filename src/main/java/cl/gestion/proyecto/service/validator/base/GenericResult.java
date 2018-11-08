package cl.gestion.proyecto.service.validator.base;

import java.util.Optional;

public class GenericResult {
    private boolean isValid;


    public GenericResult(boolean isValid) {
        this.isValid = isValid;
    }

    public static GenericResult ok() {
        return new GenericResult(true);
    }

    public static GenericResult fail() {
        return new GenericResult(false);
    }

    public boolean isValid() {
        return isValid;
    }

    public Optional<String> getFieldNameIfInvalid(String field) {
        return this.isValid ? Optional.empty() : Optional.of(field);
    }
}
