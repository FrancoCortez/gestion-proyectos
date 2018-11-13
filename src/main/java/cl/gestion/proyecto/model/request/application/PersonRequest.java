package cl.gestion.proyecto.model.request.application;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class PersonRequest implements Serializable {
    private String rut;
    private String name;
    private String lastName;
    private Date birthDate;
    private String address;
    private Integer phoneNumber;
}
