package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.GrowthPhase;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PulverisationSampleOccurrence implements Serializable {
    
    @Temporal (TemporalType.DATE)
    private Date pulverisationDate;
    
    @Enumerated (EnumType.STRING)
    private GrowthPhase growthPhase;
    
    private double caldaVolume;
    private int daysAfterEmergence;

    @ManyToOne(fetch = FetchType.EAGER)
    protected Pest pest;
    
    public String getPestUsualName() {
        return this.getPest().getUsualName();
    }
    
    public String getPestScientificName() {
        return this.getPest().getScientificName();
    }
    
    public String getPestSizeName() {
        return this.getPest().getPestSizeName();
    }
}
