package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.apache.commons.text.WordUtils;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PestNaturalPredator extends MIPEntity implements Serializable {
    
    @EqualsAndHashCode.Include
    @Size(min = 5, max = 50, message = "O nome deve ter entre 5 e 50 caracteres")
    private String usualName;
    
    public void setUsualName (String usualName) {
        this.usualName = WordUtils.capitalize(usualName.toLowerCase());
    }
    
    @Builder
    public static PestNaturalPredator create (Long id, String usualName) {
        PestNaturalPredator instance = new PestNaturalPredator();
        instance.setId(id);
        instance.setUsualName(usualName);
        
        return instance;
    }    
}
