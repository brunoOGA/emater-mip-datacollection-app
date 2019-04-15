package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.text.WordUtils;

@Entity
@Getter (AccessLevel.PUBLIC)
@Setter (AccessLevel.PACKAGE)
@EqualsAndHashCode (onlyExplicitlyIncluded = true)
public class Region extends AuditingPersistenceEntity implements Serializable {
    
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @EqualsAndHashCode.Include
    private String name;
    
    @EqualsAndHashCode.Include
    @ManyToOne (fetch = FetchType.EAGER)
    private MacroRegion macroRegion;

    @OneToMany
    private Set<City> cities;
    
    @Builder
    public static Region create (String name, MacroRegion macroRegion) {
        Region result = new Region();
        result.setName(name);
        result.setMacroRegion(macroRegion);
        
        return result;
    }

    void setName (String name) {
        this.name = WordUtils.capitalize(name.toLowerCase());
    }

    public boolean addCity (City city) {

        if (isThereACityContainer())
            createCityContainer();
        
        return this.getCities().add(city);
    }

    private boolean isThereACityContainer() {
        return this.getCities() == null;
    }

    private void createCityContainer() {
        this.setCities(new HashSet<City>());
    }
}
