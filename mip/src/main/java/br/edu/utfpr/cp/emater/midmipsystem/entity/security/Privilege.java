package br.edu.utfpr.cp.emater.midmipsystem.entity.security;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Privilege implements Serializable {
    
    @Id @GeneratedValue 
    private Long id;
 
    @Size (min = 5, message = "O nome precisa ter pelo menos 5 caracteres")
    private String name;
    
    @Enumerated (EnumType.STRING)
    private PrivilegeType type;
    
    @Size (min = 5, message = "A URL precisa ter pelo menos 5 caracteres")
    private String urlAllowed;
 
    @ManyToMany(mappedBy = "privileges")
    private List<Role> roles;
}
