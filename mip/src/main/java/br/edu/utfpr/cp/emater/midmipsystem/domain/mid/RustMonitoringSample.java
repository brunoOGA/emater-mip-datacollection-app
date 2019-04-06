package br.edu.utfpr.cp.emater.midmipsystem.domain.mid;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class RustMonitoringSample implements Serializable {

    @Id @GeneratedValue
    private Long id;
    private Long surveyFieldId;

    @Basic
    private LocalDate collectorInstallDate;
    
    @Getter (value = AccessLevel.NONE)
    @ElementCollection
    private Set<RustMonitoringSampleOccurrence> occurrences;

    public Set<RustMonitoringSampleOccurrence> getOccurrences() {
        return Set.copyOf(this.occurrences);
    }

    @Builder
    public static RustMonitoringSample create (Long surveyFieldId, LocalDate collectorInstallDate) {
        RustMonitoringSample sample = new RustMonitoringSample();

        sample.surveyFieldId = surveyFieldId;
        sample.collectorInstallDate = collectorInstallDate;

        return sample;
    }

    public boolean addSample (RustMonitoringSampleOccurrence anOccurrence) {
        if (this.occurrences == null)
            this.occurrences = new HashSet<>();

        return this.occurrences.add (anOccurrence);
    }

    public boolean removeSample (RustMonitoringSampleOccurrence anOccurrence) {
        return this.occurrences.remove(anOccurrence);
    }
}