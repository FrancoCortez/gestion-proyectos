package cl.gestion.proyecto.model.request.domain;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
public class TypeUserRequest implements Serializable {

    @NotBlank
    @NotNull
    private String name;
    private String description;
}
