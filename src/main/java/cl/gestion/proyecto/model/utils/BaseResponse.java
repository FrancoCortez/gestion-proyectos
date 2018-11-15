package cl.gestion.proyecto.model.utils;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
public class BaseResponse implements Serializable {

    private Object data;
}
