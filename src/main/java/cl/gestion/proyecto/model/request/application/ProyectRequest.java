package cl.gestion.proyecto.model.request.application;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class ProyectRequest implements Serializable {

    private String name;
    private String description;
    private Date init;
    private Date finish;
    private Set<String> proyectBoos;
    private Set<String> comercialManager;
    private String proyectStateId;
    private String clientId;

}
