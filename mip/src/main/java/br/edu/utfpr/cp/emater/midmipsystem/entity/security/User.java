package br.edu.utfpr.cp.emater.midmipsystem.entity.security;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User implements Serializable {
    
    @Id @GeneratedValue
    private Long id;
    
    @Size (min = 5, message = "O nome completo deve ser maior que 5 caracteres")
    private String fullName;
    
    @Email (message = "Deve ser informado um e-mail válido")
    @EqualsAndHashCode.Include
    private String email;
    
    @Size (min = 3, max = 20, message = "O nome de usuário deve ter entre 3 e 20 caracteres")
    private String username;
    
    @Size (min = 3, message = "A senha deve ser maior que 3 caracteres")
    private String password;
    
    @ManyToOne
    private Region region;
    
    @ManyToOne
    private City city;
    
    private boolean accountNonExpired;
    
    private boolean accountNonLocked;
    
    private boolean credentialsNonExpired;
    
    private boolean enabled;
    
    @ManyToMany (fetch = FetchType.EAGER)
    private List<Authority> authorities;

}
