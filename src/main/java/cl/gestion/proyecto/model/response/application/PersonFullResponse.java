package cl.gestion.proyecto.model.response.application;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class PersonFullResponse implements Serializable {
    private String rut;
    private String name;
    private String lastName;
    private Date birthDate;
    private String address;
    private Integer phoneNumber;
}
