package cl.gestion.proyecto.model.entities.base;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BaseEntity implements Serializable {

    @Id
    private String _id;
    private AuditingEntity auditing;
}
