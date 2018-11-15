package cl.gestion.proyecto.model.response.application;

import cl.gestion.proyecto.model.response.domain.ProyectStateFullResponse;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@EqualsAndHashCode
public class ProyectFullResponse implements Serializable {
    private String _id;
    private String name;
    private String description;
    private Date init;
    private Date finish;
    private Set<UserFullResponse> proyectBoos;
    private Set<UserFullResponse> comercialManager;
    private ProyectStateFullResponse proyectState;
    private ClientFullResponse client;
}
