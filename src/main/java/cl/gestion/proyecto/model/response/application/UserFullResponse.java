package cl.gestion.proyecto.model.response.application;

import cl.gestion.proyecto.model.entities.application.RoleEntity;
import cl.gestion.proyecto.model.response.domain.TypeUserFullResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class UserFullResponse implements Serializable {

    private String username;
    private String mail;
    private Boolean enabled;
    private List<RoleEntity> roles;
    @JsonIgnore
    private String typeUserId;
    private TypeUserFullResponse typeUser;

}
