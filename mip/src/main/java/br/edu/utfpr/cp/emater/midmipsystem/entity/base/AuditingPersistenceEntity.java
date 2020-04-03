package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.security.MIPUser;
import java.io.Serializable;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data 
@MappedSuperclass
@EntityListeners (AuditingEntityListener.class)
public abstract class AuditingPersistenceEntity implements Serializable {

    @CreatedDate
    private Long createdAt;

    @LastModifiedDate
    private Long lastModified;
    
    @CreatedBy
    @ManyToOne
    private MIPUser createdBy;
    
    @LastModifiedBy
    @ManyToOne
    private MIPUser modifiedBy;
}
