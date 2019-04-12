package br.edu.utfpr.cp.emater.midmipsystem.domain.base.macroRegion;

import br.edu.utfpr.cp.emater.midmipsystem.library.AuditingPersistenceEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.text.WordUtils;

@Entity
@Getter
@Setter
@EqualsAndHashCode (onlyExplicitlyIncluded = true)
public class MacroRegion extends AuditingPersistenceEntity implements Serializable {
    
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @EqualsAndHashCode.Include
    private String name;
    
    public void setName (String name) {
        this.name = WordUtils.capitalize(name.toLowerCase());
    }

    @Builder
    public static MacroRegion create (String name) {
        MacroRegion instance = new MacroRegion();
        instance.setName(name);
        
        return instance;
    }
}