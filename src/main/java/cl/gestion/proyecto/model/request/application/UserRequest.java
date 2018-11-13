package cl.gestion.proyecto.model.request.application;

import cl.gestion.proyecto.model.entities.application.RoleEntity;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class UserRequest implements Serializable {
    private String username;
    private String password;
    private String mail;
    private String typeUserId;
    private List<RoleEntity> roles;
    private PersonRequest personalData;
}
