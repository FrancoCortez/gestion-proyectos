package cl.gestion.proyecto.model.utils;

import lombok.*;
import reactor.core.publisher.Mono;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class HandlerResponse<T> implements Serializable {

    private Integer status;
    private Mono<T> data;
    private Class<T> cla;

}
