package cl.gestion.proyecto.model.entities.application;

import lombok.*;
import org.springframework.data.mongodb.core.index.TextIndexed;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class PersonEntity {

    @TextIndexed
    private String rut;
    private String name;
    private String lastName;
    @TextIndexed
    private String mail;
    private Date birthDate;
    private String address;
    private Integer phoneNumber;
}
