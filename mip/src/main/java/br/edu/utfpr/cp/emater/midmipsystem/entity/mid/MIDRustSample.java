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
        
        var leafOccurrenceAsOptional = Optional.ofNullable(this.leafInspectionOccurrence).map(currentOccurrence -> currentOccurrence.getBladeReadingRustResultLeafInspection());

        var collectorResult = collectorOccurrenceAsOptional
                .filter(currentOccurrence -> currentOccurrence.equals(AsiaticRustTypesSporeCollector.SEM_ESPOROS_FERRUGEM))
                .map(currentOccurrence -> true)
                .orElse(false);
        
        var leafResult = leafOccurrenceAsOptional
                .filter(currentOccurrence -> currentOccurrence.equals(AsiaticRustTypesLeafInspection.SEM_FERRUGEM_COM_SINAIS_OUTRAS_DOENCAS))
                .map(currentOccurrent -> true)
                .orElse(false);

        return collectorResult || leafResult;
    }
}
