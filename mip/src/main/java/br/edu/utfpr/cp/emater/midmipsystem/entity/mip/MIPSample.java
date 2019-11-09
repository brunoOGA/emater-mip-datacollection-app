package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.AuditingPersistenceEntity;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
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
public class MIPSample extends AuditingPersistenceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Temporal(TemporalType.DATE)
    private Date sampleDate;

    private int defoliation;

    @Enumerated(EnumType.STRING)
    private GrowthPhase growthPhase;

    @ElementCollection
    private Set<MIPSamplePestOccurrence> mipSamplePestOccurrence;

    @ElementCollection
    private Set<MIPSamplePestDiseaseOccurrence> mipSamplePestDiseaseOccurrence;

    @ElementCollection
    private Set<MIPSampleNaturalPredatorOccurrence> mipSampleNaturalPredatorOccurrence;

    @EqualsAndHashCode.Include
    @ManyToOne
    private Survey survey;

    @Builder
    public static MIPSample create(Long id,
            Date sampleDate,
            int daysAfterEmergence,
            int defoliation,
            GrowthPhase growthPhase,
            Survey survey) {

        var instance = new MIPSample();
        instance.setId(id);
        instance.setSampleDate(sampleDate);
        instance.setDefoliation(defoliation);
        instance.setGrowthPhase(growthPhase);
        instance.setSurvey(survey);

        return instance;
    }

    public boolean addPestOccurrence(Pest pest, double value) {

        if (this.getMipSamplePestOccurrence() == null) {
            this.setMipSamplePestOccurrence(new HashSet<>());
        }

        return this.getMipSamplePestOccurrence().add(MIPSamplePestOccurrence.builder().pest(pest).value(value).build());
    }

    public boolean addPestDiseaseOccurrence(PestDisease pestDisease, double value) {

        if (this.getMipSamplePestDiseaseOccurrence() == null) {
            this.setMipSamplePestDiseaseOccurrence(new HashSet<>());
        }

        return this.getMipSamplePestDiseaseOccurrence().add(MIPSamplePestDiseaseOccurrence.builder().pestDisease(pestDisease).value(value).build());
    }

    public boolean addPestNaturalPredatorOccurrence(PestNaturalPredator pestNaturalPredator, double value) {

        if (this.getMipSampleNaturalPredatorOccurrence() == null) {
            this.setMipSampleNaturalPredatorOccurrence(new HashSet<>());
        }

        return this.getMipSampleNaturalPredatorOccurrence().add(MIPSampleNaturalPredatorOccurrence.builder().pestNaturalPredator(pestNaturalPredator).value(value).build());
    }

    public String getHarvestName() {
        if (this.getSurvey() != null) {
            return this.getSurvey().getHarvestName();
        }

        return null;
    }

    public String getFarmerName() {
        if (this.getSurvey() != null) {
            return this.getSurvey().getFarmerString();
        }

        return null;
    }

    public String getFieldName() {
        if (this.getSurvey() != null) {
            return this.getSurvey().getFieldName();
        }

        return null;
    }

    public String getCityName() {
        if (this.getSurvey() != null) {
            return this.getSurvey().getFieldCityName();
        }

        return null;
    }

    public String getSupervisorNames() {
        if (this.getSurvey() != null) {
            return this.getSurvey().getField().getSupervisorNames().toString();
        }

        return null;
    }

    public String getCultivarName() {
        if (this.getSurvey() != null) {
            return this.getSurvey().getCultivarName();
        }

        return null;
    }

    public int getDAE() {
        if (this.getSurvey() == null) {
            return 0;
        }

        if (this.getSurvey().getEmergenceDate() == null) {
            return 0;
        }

        if (this.getSampleDate() == null) {
            return 0;
        }

        long diffInMillies = (this.getSampleDate().getTime() - this.getSurvey().getEmergenceDate().getTime());

        if (diffInMillies > 0) {
            var result = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            return (int) (result + 1);

        } else {
            return 0;

        }
    }

    public Optional<MIPSamplePestOccurrence> getOccurrenceByPest(Pest aPest) {

        if (aPest == null) {
            return Optional.empty();
        }

        return this.getMipSamplePestOccurrence()
                .stream()
                .filter(currentOccurrence -> currentOccurrence.getPest().equals(aPest))
                .findFirst();
    }

    public Optional<Map<Integer, Double>> getDAEAndPestOccurrenceByPest(Pest aPest) {

        if (aPest == null) {
            return Optional.empty();
        }

        var occurrenceByPest = this.getOccurrenceByPest(aPest);

        if (occurrenceByPest.isEmpty()) {
            return Optional.empty();
        }

        var result = new HashMap<Integer, Double>();

        occurrenceByPest.ifPresent(occurrence -> result.put(this.getDAE(), occurrence.getValue()));

        return Optional.of(result);
    }

    public Optional<Map<Integer, Double>> getDAEAndPestOccurrenceByPestSet(Set<Pest> pests) {
        
        if (pests == null)
            return Optional.empty();
        
        if (pests.size() == 0)
            return Optional.empty();
        
        var result = new HashMap<Integer, Double>();
        
        pests.stream()
                .map(currentPest -> this.getDAEAndPestOccurrenceByPest(currentPest))
                .filter(currentOptionalOccurrence -> currentOptionalOccurrence.isPresent())
                .forEach(currentOptionalOccurrence -> result.putAll(currentOptionalOccurrence.get()));
        
        if (result.isEmpty())
            return Optional.empty();
        
        else
            return Optional.of(result);
    }

}
