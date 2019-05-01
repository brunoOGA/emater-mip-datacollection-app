package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode (onlyExplicitlyIncluded = true)
public class MIPSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Temporal(TemporalType.DATE)
    private Date sampleDate;

    private int daysAfterEmergence;
    private int defoliation;

    @Enumerated(EnumType.STRING)
    private GrowthPhase growthPhase;

    @ElementCollection
    private Set<MIPSampleOccurrence> mipSampleOccurrences;

    @EqualsAndHashCode.Include
    @ManyToOne
    private Survey survey;
    
    @Builder
    public static MIPSample create (Long id,
                                    Date sampleDate,
                                    int daysAfterEmergence,
                                    int defoliation,
                                    GrowthPhase growthPhase,
                                    Set<MIPSampleOccurrence> mipSampleOccurrences,
                                    Survey survey) {
        
        var instance = new MIPSample();
        instance.setId(id);
        instance.setSampleDate(sampleDate);
        instance.setDaysAfterEmergence(daysAfterEmergence);
        instance.setDefoliation(defoliation);
        instance.setGrowthPhase(growthPhase);
        instance.setMipSampleOccurrences(mipSampleOccurrences);
        instance.setSurvey(survey);
        
        return instance;
    }
    
    public boolean addSampleOccurrence (MIPEntity entity, double value) {
        if (this.getMipSampleOccurrences() == null)
            this.setMipSampleOccurrences(new HashSet<MIPSampleOccurrence>());
        
        return this.getMipSampleOccurrences().add(MIPSampleOccurrence.builder().mipEntity(entity).value(value).build());
    }
}
