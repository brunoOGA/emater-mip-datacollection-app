package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.AuditingPersistenceEntity;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "A data da coleta precisa ser informada!")
    private Date sampleDate;

    @Embedded
    private MIDSampleSporeCollectorOccurrence sporeCollectorOccurrence;

    @Embedded
    private MIDSampleLeafInspectionOccurrence leafInspectionOccurrence;

    @Builder
    public static MIDRustSample create(Long id,
            Survey survey,
            Date sampleDate) {
        var instance = new MIDRustSample();
        instance.setId(id);
        instance.setSurvey(survey);
        instance.setSampleDate(sampleDate);

        return instance;
    }

    public Optional<Date> getSampleDateAsOptional() {
        return Optional.ofNullable(this.sampleDate);
    }

    public boolean isSporePresent() {

        var collectorOccurrenceAsOptional = Optional.ofNullable(this.sporeCollectorOccurrence).map(currentOccurrence -> currentOccurrence.getBladeReadingRustResultCollector());

        if (collectorOccurrenceAsOptional.isPresent()) {
            switch (collectorOccurrenceAsOptional.get()) {
                case COM_ESPOROS_INVIAVEIS_APOS_TESTE:
                case COM_ESPOROS_SEM_TESTAR_VIABILIDADE:
                case COM_ESPOROS_VIAVEIS_AGLOMERADOS:
                case COM_ESPOROS_VIAVEIS_ISOLADOS:
                    return true;
                    
                case SEM_ESPOROS_FERRUGEM:
                    return false;
                    
                default:
                    return false;
            }
        }
            
        return false;
    }
}
