package cl.gestion.proyecto.model.utils;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
public class ErrorHandlerResponse implements Serializable {

    private String msg;
    private Object trace;
    private Object cause;
}
