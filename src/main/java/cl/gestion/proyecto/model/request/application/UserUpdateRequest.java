package cl.gestion.proyecto.model.request.application;

import cl.gestion.proyecto.model.entities.application.RoleEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class UserUpdateRequest implements Serializable {

    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String mail;
    private String typeUserId;
    private List<RoleEntity> roles;
}
