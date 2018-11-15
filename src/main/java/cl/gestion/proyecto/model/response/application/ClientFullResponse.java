package cl.gestion.proyecto.model.response.application;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@EqualsAndHashCode
public class ClientFullResponse implements Serializable {

    private String rut;
    private String name;
    private String address;
    private Integer phoneNumber;
    private Set<UserFullResponse> users;
}
