package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.security.MIPUser;
import br.edu.utfpr.cp.emater.midmipsystem.entity.security.MIPUserPrincipal;
import java.io.Serializable;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreRemove;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

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
    
    @PreRemove
    public void onPreRemoveEvent() throws AccessDeniedException {
        
        var loggedUser = ((MIPUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        var createdByName = this.getCreatedBy() != null ? this.getCreatedBy().getUsername() : "none";

        if (loggedUser.getAuthorities().stream().noneMatch(currentAuthority -> currentAuthority.getId().equals(1L))) {
            if (!loggedUser.getUsername().equalsIgnoreCase(createdByName)) {
                throw new AccessDeniedException("Usuário não autorizado para essa exclusão!");
            }
        }
    }
}
