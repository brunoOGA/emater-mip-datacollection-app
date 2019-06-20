package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.AuditingPersistenceEntity;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.GrowthPhase;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import java.io.Serializable;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MIDRustSample extends AuditingPersistenceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @ManyToOne
    private Survey survey;

    @EqualsAndHashCode.Include
    @Temporal(TemporalType.DATE)
    private Date sampleDate;
    
    @ElementCollection
    private Set<MIDSampleSporeCollectorOccurrence> sporeCollectorOccurrence;
    
    @ElementCollection
    private Set<MIDSampleLeafInspectionOccurrence> leafInspectionOccurrence;
    
    @ElementCollection
    private Set<MIDSampleFungicideApplicationOccurrence> fungicideOccurrence;

    @Builder
    public static MIDRustSample create (Long id,
                                                  Survey  survey,
                                                  Date sampleDate) {
        var instance = new MIDRustSample();
        instance.setId(id);
        instance.setSurvey(survey);
        instance.setSampleDate(sampleDate);
        
        return instance;
    }
    
    public boolean addSporeCollectorOccurrence(boolean bladeInstalledPreCold, 
                                               String bladeReadingResponsibleName, 
                                               String bladeReadingResponsibleEntityName, 
                                               Date bladeReadingDate, 
                                               AsiaticRustTypesSporeCollector bladeReadingRustResultCollector) {
        

        if (this.getSporeCollectorOccurrence() == null) {
            this.setSporeCollectorOccurrence(new HashSet<>());
        }

        return this.getSporeCollectorOccurrence().add(MIDSampleSporeCollectorOccurrence.builder()
                                                            .bladeInstalledPreCold(bladeInstalledPreCold)
                                                            .bladeReadingDate(bladeReadingDate)
                                                            .bladeReadingResponsibleName(bladeReadingResponsibleName)
                                                            .bladeReadingResponsibleEntityName(bladeReadingResponsibleEntityName)
                                                            .bladeReadingRustResultCollector(bladeReadingRustResultCollector)
                                                            .build()
                                                     );
    }
    
    public boolean addLeafInspectionOccurrence(GrowthPhase growthPhase,
                                               AsiaticRustTypesLeafInspection bladeReadingRustResultLeafInspection) {
        
        if (this.getLeafInspectionOccurrence() == null) {
            this.setLeafInspectionOccurrence(new HashSet<>());
        }

        return this.getLeafInspectionOccurrence().add(MIDSampleLeafInspectionOccurrence.builder()
                                                            .bladeReadingRustResultLeafInspection(bladeReadingRustResultLeafInspection)
                                                            .growthPhase(growthPhase)
                                                            .build()
                                                     );
    }
    
    public boolean addFungicideOccurrence(boolean asiaticRustApplication,
                                          boolean otherDiseasesApplication,
                                          Date fungicideApplicationDate,
                                          String notes) {
        
        if (this.getFungicideOccurrence() == null) {
            this.setFungicideOccurrence(new HashSet<>());
        }

        return this.getFungicideOccurrence().add(MIDSampleFungicideApplicationOccurrence.builder()
                                                    .asiaticRustApplication(asiaticRustApplication)
                                                    .fungicideApplicationDate(fungicideApplicationDate)
                                                    .notes(notes)
                                                    .otherDiseasesApplication(otherDiseasesApplication)
                                                    .build()
                                                 );
    }
}
