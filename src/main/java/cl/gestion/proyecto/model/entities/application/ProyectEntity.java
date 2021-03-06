package cl.gestion.proyecto.model.entities.application;

import cl.gestion.proyecto.model.entities.base.BaseEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
@Document(collection = "proyect")
public class ProyectEntity extends BaseEntity {
    private String name;
    private String description;
    private Date init;
    private Date finish;
    private Set<String> proyectBoos;
    private Set<String> comercialManager;
    private String proyectStateId;
    private String clientId;
}
