package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.PulverisationSampleOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.AuditingPersistenceEntity;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode (onlyExplicitlyIncluded = true)
public class PulverisationSample extends AuditingPersistenceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private double soyaPrice;
    private double operationCost;    
    
    @ElementCollection
    private Set<PulverisationSampleOccurrence> pulvarisationOccurrence;

    @EqualsAndHashCode.Include
    @ManyToOne
    private Survey survey;

    @Builder
    public static PulverisationSample create(Long id,
            double soyaPrice,
            double operationCost,
            Survey survey) {

        var instance = new PulverisationSample();
        instance.setId(id);
        instance.setSoyaPrice(soyaPrice);
        instance.setOperationCost(operationCost);
        instance.setSurvey(survey);

        return instance;
    }

    public String getHarvestName() {
        return this.getSurvey().getHarvestName();
    }

    public String getFarmerName() {
        return this.getSurvey().getFarmerString();
    }

    public String getFieldName() {
        return this.getSurvey().getFieldName();
    }

    public String getCityName() {
        return this.getSurvey().getFieldCityName();
    }

    public String getSupervisorNames() {
        return this.getSurvey().getField().getSupervisorNames().toString();
    }

    public String getSeedName() {
        return this.getSurvey().getSeedName();
    }
}
