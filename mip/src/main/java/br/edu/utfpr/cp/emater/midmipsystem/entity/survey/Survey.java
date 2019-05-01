package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.AuditingPersistenceEntity;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.State;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;

import org.apache.commons.text.WordUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode (onlyExplicitlyIncluded = true)
public class Survey extends AuditingPersistenceEntity implements Serializable {

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(min = 3, max = 50, message = "A identificação da cultivar deve ter entre 3 e 50 caracteres")
    private String seedName;
    private boolean sporeCollectorPresent;
    
    @Embedded
    private QuestionData questionData;
    
    @Embedded
    private DateData dateData;
    
    @Embedded
    private SizeData sizeData;
    
    @Embedded
    private LocationData locationData;
    
    @Embedded
    private ProductivityData productivityData;
    
    @ManyToOne (fetch = FetchType.EAGER)
    @EqualsAndHashCode.Include
    @NotNull (message = "Uma pesquisa deve conter uma unidade de referência")
    private Field field;
    
    @ManyToOne (fetch = FetchType.EAGER)
    @EqualsAndHashCode.Include
    @NotNull (message = "Uma pesquisa deve ser referente a uma safra")
    private Harvest harvest;

    public void setSeedName (String seedName) {
        this.seedName = WordUtils.capitalize(seedName.toLowerCase());
    }
    
    @Builder
    public static Survey create (Long id, 
                                 String seedName, 
                                 boolean sporeCollectorPresent, 
                                 boolean rustResistant, 
                                 boolean bt, 
                                 Date sowedDate, 
                                 Date emergenceDate, 
                                 Date harvestDate,
                                 double longitude,
                                 double latitude,
                                 double productivityField,
                                 double productivityFarmer,
                                 boolean separatedWeight,
                                 double totalArea,
                                 double totalPlantedArea,
                                 double plantPerMeter,
                                 Field field,
                                 Harvest harvest) {
                
        var instance = new Survey();
        instance.setId(id);
        instance.setSeedName(seedName);
        instance.setSporeCollectorPresent(sporeCollectorPresent);
        instance.setField(field);
        instance.setHarvest(harvest);
        
        instance.setDateData(DateData.builder().emergenceDate(emergenceDate).harvestDate(harvestDate).sowedDate(sowedDate).build());
        instance.setLocationData(LocationData.builder().latitude(latitude).longitude(longitude).build());
        instance.setProductivityData(ProductivityData.builder().productivityFarmer(productivityFarmer).productivityField(productivityField).separatedWeight(separatedWeight).build());
        instance.setQuestionData(QuestionData.builder().bt(bt).rustResistant(rustResistant).build());
        instance.setSizeData(SizeData.builder().plantPerMeter(plantPerMeter).totalArea(totalArea).totalPlantedArea(totalPlantedArea).build());
        
        return instance;
    }
    
    public boolean isRustResistant() {
        return this.getQuestionData().isRustResistant();
    }
    
    public boolean isBt() {
        return this.getQuestionData().isBt();
    }
    
    public Date getSowedDate() {
        return this.getDateData().getSowedDate();
    }
    
    public Date getEmergenceDate() {
        return this.getDateData().getEmergenceDate();
    }
    
    public Date getHarvestDate() {
        return this.getDateData().getHarvestDate();
    }
    
    public double getLongitude() {
        return this.getLocationData().getLongitude();
    }
    
    public double getLatitude() {
        return this.getLocationData().getLatitude();
    }
    
    public double getProductivityField() {
        return this.getProductivityData().getProductivityField();
    }
    
    public double getProductivityFarmer() {
        return this.getProductivityData().getProductivityFarmer();
    }
    
    public boolean isSeparatedWeight() {
        return this.getProductivityData().isSeparatedWeight();
    }
    
    public double getTotalArea() {
        return this.getSizeData().getTotalArea();
    }
    
    public double getTotalPlantedArea() {
        return this.getSizeData().getTotalPlantedArea();
    }
    
    public double getPlantPerMeter() {
        return this.getSizeData().getPlantPerMeter();
    }
    
    public Long getFieldId() {
        return this.getField().getId();
    }
    
    public String getFieldName() {
        return this.getField().getName();
    }
    
    public String getFieldLocation() {
        return this.getField().getLocation();
    }
    
    public Long getFieldCityId() {
        return this.getField().getCityId();
    }
    
    public String getFieldCityName() {
        return this.getField().getCityName();
    }
    
    public State getFieldCityState() {
        return this.getField().getCity().getState();
    }
    
    public Long getFarmerId() {
        return this.getField().getFarmerId();
    }   
    
    public String getFarmerString() {
        return this.getField().getFarmerName();
    }
    
    public Set<Supervisor> getFieldSupervisors() {
        return this.getField().getSupervisors();
    }
    
    public Long getHarvestId() {
        return this.getHarvest().getId();
    }
    
    public String getHarvestName() {
        return this.getHarvest().getName();
    }
    
    public Date getHarvestBeginDate() {
        return this.getHarvest().getBegin();
    }
    
    public Date getHarvestEndDate() {
        return this.getHarvest().getEnd();
    }
    
    public String getFieldSupervisorNames() {
        return this.getField().getSupervisorNames().toString();
    }
    
}
