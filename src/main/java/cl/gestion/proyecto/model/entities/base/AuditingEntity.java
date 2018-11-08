package cl.gestion.proyecto.model.entities.base;

import lombok.*;
import org.springframework.data.annotation.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
public class AuditingEntity implements Serializable {

    @CreatedBy
    private String createdBy;
    @CreatedDate
    private Date createdDate;
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    private Date lastModfiedDate;
    @Version
    private Long version;
    private Boolean delete;
}
