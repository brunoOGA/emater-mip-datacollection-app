package br.edu.utfpr.cp.emater.midmipsystem.view.survey;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.view.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.MacroRegionService;
import br.edu.utfpr.cp.emater.midmipsystem.view.ICRUDController;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.SupervisorNotAllowedInCity;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.HarvestService;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyService;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class SurveyController extends Survey implements ICRUDController<Survey> {

    private final SurveyService surveyService;

    @Getter
    @Setter
    private Long selectedHarvestId;
    
    @Getter
    @Setter
    private Long selectedFieldId;

    @Getter
    @Setter
    private boolean rustResistant;

    @Getter
    @Setter
    private boolean bt;

    @Getter
    @Setter
    private double totalArea;

    @Getter
    @Setter
    private double totalPlantedArea;

    @Getter
    @Setter
    private double plantPerMeter;

    @Getter
    @Setter
    private double longitude;

    @Getter
    @Setter
    private double latitude;

    @Getter
    @Setter
    private double productivityField;

    @Getter
    @Setter
    private double productivityFarmer;

    @Getter
    @Setter
    private boolean separatedWeight;

    @Getter
    @Setter
    private Date sowedDate;

    @Getter
    @Setter
    private Date emergenceDate;

    @Getter
    @Setter
    private Date harvestDate;

    @Autowired
    public SurveyController(SurveyService aSurveyService) {
        this.surveyService = aSurveyService;
    }

    @Override
    public List<Survey> readAll() {
        return surveyService.readAll();
    }

    public List<Harvest> readAllHarvests() {
        return surveyService.readAllHarvests();
    }

    public List<Field> readAllFieldsOutOfCurrentSurvey() {
        return surveyService.readAllFieldsOutOfCurrentHarvest(this.getSelectedHarvestId());
    }

    public String selectHarvest() {

        try {
            var selectedHarvest = surveyService.readHarvestById(this.getSelectedHarvestId());

            this.setHarvest(selectedHarvest);
            this.setSelectedHarvestId(selectedHarvest.getId());

            return "create.xhtml";

        } catch (EntityNotFoundException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Safra não pode ser selecionada porque não foi encontrada na base de dados!"));
            return "index.xhtml";
        }

    }

    @Override
    public String create() {

        try {
            var newSurvey = Survey.builder()
                    .bt(this.isBt())
                    .emergenceDate(this.getEmergenceDate())
                    .field(this.getField())
                    .harvest(surveyService.readHarvestById(this.getSelectedHarvestId()))
                    .latitude(this.getLatitude())
                    .longitude(this.getLongitude())
                    .plantPerMeter(this.getPlantPerMeter())
                    .productivityFarmer(this.getProductivityFarmer())
                    .productivityField(this.getProductivityField())
                    .rustResistant(this.isRustResistant())
                    .seedName(this.getSeedName())
                    .separatedWeight(this.isSeparatedWeight())
                    .sowedDate(this.getSowedDate())
                    .harvestDate(this.getHarvestDate())
                    .sporeCollectorPresent(this.isSporeCollectorPresent())
                    .totalArea(this.getTotalArea())
                    .totalPlantedArea(this.totalPlantedArea)
                    .build();

            surveyService.create(newSurvey);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", String.format("UR [%s] adicionada na pesquisa da [%s]", newSurvey.getFieldName(), newSurvey.getHarvestName())));

            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "A unidade de referência já faz parte dessa pesquisa."));
            return "create.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível adicionar a UR à pesquisa porque a safra ou a UR não foram encontradas na base de dados!"));
            return "index.xhtml";

        } catch (AnyPersistenceException | SupervisorNotAllowedInCity ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";

        }
    }

    @Override
    public String prepareUpdate(Long anId) {

        try {
            var existentSurvey = surveyService.readById(anId);

            this.setId(existentSurvey.getId());
            this.setSeedName(existentSurvey.getSeedName());
            this.setSporeCollectorPresent(existentSurvey.isSporeCollectorPresent());
            
            this.setField(existentSurvey.getField());
            this.setSelectedFieldId(existentSurvey.getFieldId());
            
            this.setHarvest(existentSurvey.getHarvest());
            this.setSelectedHarvestId(existentSurvey.getHarvestId());

            this.setBt(existentSurvey.isBt());
            this.setEmergenceDate(existentSurvey.getEmergenceDate());
            this.setHarvestDate(existentSurvey.getHarvestDate());
            this.setLatitude(existentSurvey.getLatitude());
            this.setLongitude(existentSurvey.getLongitude());
            this.setPlantPerMeter(existentSurvey.getPlantPerMeter());
            this.setProductivityFarmer(existentSurvey.getProductivityFarmer());
            this.setProductivityField(existentSurvey.getProductivityField());
            this.setRustResistant(existentSurvey.isRustResistant());
            this.setSeparatedWeight(existentSurvey.isSeparatedWeight());
            this.setSowedDate(existentSurvey.getSowedDate());
            this.setTotalArea(existentSurvey.getTotalArea());
            this.setTotalPlantedArea(existentSurvey.getTotalPlantedArea());
            
            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Dados não puderam ser alterados porque não foram encontrados na base de dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String update() {

        try {

            var updatedSurvey = Survey.builder()
                    .id(this.getId())
                    .bt(this.isBt())
                    .emergenceDate(this.getEmergenceDate())
                    .field(surveyService.readFieldbyId(this.getSelectedFieldId()))
                    .harvest(surveyService.readHarvestById(this.getSelectedHarvestId()))
                    .latitude(this.getLatitude())
                    .longitude(this.getLongitude())
                    .plantPerMeter(this.getPlantPerMeter())
                    .productivityFarmer(this.getProductivityFarmer())
                    .productivityField(this.getProductivityField())
                    .rustResistant(this.isRustResistant())
                    .seedName(this.getSeedName())
                    .separatedWeight(this.isSeparatedWeight())
                    .sowedDate(this.getSowedDate())
                    .harvestDate(this.getHarvestDate())
                    .sporeCollectorPresent(this.isSporeCollectorPresent())
                    .totalArea(this.getTotalArea())
                    .totalPlantedArea(this.totalPlantedArea)
                    .build();

            surveyService.update(updatedSurvey);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "UR alterada!"));
            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "A unidade de referência já faz parte dessa pesquisa."));
            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",  "Não foi possível adicionar a UR à pesquisa porque a safra ou a UR não foram encontradas na base de dados!"));
            return "update.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }

    }
//
//    @Override
//    public String prepareDelete(Long anId) {
//
//        try {
//            var existentHarvest = harvestService.readById(anId);
//            
//            this.setId(existentHarvest.getId());
//            this.setName(existentHarvest.getName());
//
//            return "delete.xhtml";
//
//        } catch (EntityNotFoundException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Safra não pode ser excluída porque não foi encontrada na base de dados!"));
//            return "index.xhtml";
//        }
//    }
//
//    @Override
//    public String delete() {
//        
//        try {
//            harvestService.delete(this.getId());
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Safra excluída!"));
//            return "index.xhtml";
//
//        } catch (EntityNotFoundException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Safra não pode ser excluída porque não foi encontrada na base de dados!"));
//            return "index.xhtml";
//            
//        } catch (EntityInUseException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Safra não pode ser excluída porque está sendo usada em uma pesquisa!"));
//            return "index.xhtml";
//            
//        } catch (AnyPersistenceException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
//            return "index.xhtml";
//        }
//    }


    @Override
    public String prepareDelete(Long anId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
