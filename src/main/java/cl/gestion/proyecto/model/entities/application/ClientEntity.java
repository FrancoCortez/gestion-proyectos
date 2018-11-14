package cl.gestion.proyecto.model.entities.application;

import cl.gestion.proyecto.model.entities.base.BaseEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
@Document(collection = "client")
public class ClientEntity extends BaseEntity {
    private String rut;
    private String name;
    private String address;
    private Integer phoneNumber;
    private Set<String> userId;
}
