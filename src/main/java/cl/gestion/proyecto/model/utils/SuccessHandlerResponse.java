package cl.gestion.proyecto.model.utils;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class SuccessHandlerResponse implements Serializable {

    private Object data;
}
