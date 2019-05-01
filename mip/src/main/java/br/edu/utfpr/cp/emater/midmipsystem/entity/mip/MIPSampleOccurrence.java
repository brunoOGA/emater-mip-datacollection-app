package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (onlyExplicitlyIncluded = true)
@Builder
public class MIPSampleOccurrence {
    
    @EqualsAndHashCode.Include
    private double value;
    
    @EqualsAndHashCode.Include
    @ManyToOne (fetch = FetchType.EAGER)
    private MIPEntity mipEntity;
}
