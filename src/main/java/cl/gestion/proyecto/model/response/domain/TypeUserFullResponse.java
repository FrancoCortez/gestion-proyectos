package cl.gestion.proyecto.model.response.domain;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@EqualsAndHashCode
public class TypeUserFullResponse implements Serializable {

    private String _id;
    private String name;
    private String description;
}
