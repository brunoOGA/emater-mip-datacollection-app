package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.text.WordUtils;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Region extends AuditingPersistenceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Size(min = 5, max = 50, message = "O nome da região deve ter entre 5 e 50 caracteres")
    private String name;

    @EqualsAndHashCode.Include
    @ManyToOne(fetch = FetchType.EAGER)
    private MacroRegion macroRegion;

    @OneToMany
//    @NotNull(message = "Um região deve possuir pelo menos uma cidade")
    private Set<City> cities;

    @Builder
    public static Region create(String name, MacroRegion macroRegion) {
        Region result = new Region();
        result.setName(name);
        result.setMacroRegion(macroRegion);

        return result;
    }

    public void setName(String name) {
        this.name = WordUtils.capitalize(name.toLowerCase());
    }

    public boolean addCity(City city) {

        if (isThereACityContainer())
            createCityContainer();

        return this.getCities().add(city);
    }

    public boolean removeCity(City city) {
        if (isThereACityContainer())
            return this.getCities().remove(city);
        
        return false;
    }

    private boolean isThereACityContainer() {
        return this.getCities() == null;
    }

    private void createCityContainer() {
        this.setCities(new HashSet<City>());
    }

    public String getMacroRegionName() {
        return this.getMacroRegion().getName();
    }

    public Long getMacroRegionId() {
        return this.getMacroRegion().getId();
    }

    public Set getCityNames() {
        return this.getCities();
    }

    @Override
    public String toString() {
        return String.format("[Region: name = %s, MacroRegion = %s, Cities = %s]", this.getName(), this.getMacroRegionName(), this.getCityNames().toString());
    }
    
    
}
