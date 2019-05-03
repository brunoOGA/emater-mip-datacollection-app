package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode (onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MIPSampleNaturalPredatorOccurrence implements Serializable {
    
    @EqualsAndHashCode.Include
    protected double value;
    
    @EqualsAndHashCode.Include
    @ManyToOne (fetch = FetchType.EAGER)
    protected PestNaturalPredator pestNaturalPredator;
}
