package cl.gestion.proyecto.model.entities.domain;

import cl.gestion.proyecto.model.entities.base.BaseEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@Document(collection = "proyectState")
public class ProyectStateEntity extends BaseEntity {
    private String name;
    private String description;
}
