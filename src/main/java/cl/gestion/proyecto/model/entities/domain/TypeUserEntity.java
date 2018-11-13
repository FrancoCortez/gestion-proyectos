package cl.gestion.proyecto.model.entities.domain;

import cl.gestion.proyecto.model.entities.base.BaseEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Document(collection = "typeUser")
public class TypeUserEntity extends BaseEntity {

    @TextIndexed
    @NotNull
    @NotBlank
    private String name;
    private String description;
}
