package br.edu.utfpr.cp.emater.midmipsystem.view.survey;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.SupervisorNotAllowedInCity;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyService;
import br.edu.utfpr.cp.emater.midmipsystem.view.AbstractCRUDController;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@ViewScoped
@RequiredArgsConstructor
public class SurveyController extends AbstractCRUDController<Survey> {

    private final SurveyService surveyService;

    @Getter
    @Setter
    @Size(min = 3, max = 50, message = "A identificação da cultivar deve ter entre 3 e 50 caracteres")
    private String cultivarName;

    @Getter
    @Setter
    @NotNull(message = "Uma pesquisa deve conter uma unidade de referência")
    private Field field;

    @Getter
    @Setter
    private boolean sporeCollectorPresent;

    @Getter
    @Setter
    private Date collectorInstallationDate;

    @Getter
    @Setter
    private Long selectedHarvestId;

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
    private String longitude;

    @Getter
    @Setter
    private String latitude;

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

    @Getter
    @Setter
    private Date closingDate;

    @Getter
    @Setter
    private String statusInstallationDatePanel = "hidden-sm hidden-md hidden-lg hidden-xs";

    public List<Survey> readAll() {
        return surveyService.readAll();
    }

    public List<Harvest> readAllHarvests() {
        return surveyService.readAllHarvests();
    }

    public List<Field> readAllFieldsOutOfCurrentSurvey() {
        return surveyService.readAllFields();
    }

    @Override
    protected void doCreate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
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
                .cultivarName(this.getCultivarName())
                .separatedWeight(this.isSeparatedWeight())
                .sowedDate(this.getSowedDate())
                .harvestDate(this.getHarvestDate())
                .sporeCollectorPresent(this.isSporeCollectorPresent())
                .collectorInstallationDate(this.getCollectorInstallationDate())
                .totalArea(this.getTotalArea())
                .totalPlantedArea(this.totalPlantedArea)
                .build();

        surveyService.create(newSurvey);

    }

    @Override
    protected void doPrepareUpdate(Long anId) throws EntityNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String prepareUpdate(Long surveyId) {
        try {
            var currentSurvey = surveyService.readById(surveyId);

            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentSurveyFieldName", currentSurvey.getFieldName());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentSurveyHarvestName", currentSurvey.getHarvestName());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentSurveyId", surveyId);

            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentSowedDate", currentSurvey.getSowedDate());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentEmergenceDate", currentSurvey.getEmergenceDate());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentHarvestDate", currentSurvey.getHarvestDate());

            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentProductivityField", currentSurvey.getProductivityField());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentProductivityFarmer", currentSurvey.getProductivityFarmer());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentSeparatedWeight", currentSurvey.isSeparatedWeight());

            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentSoyaPrice", currentSurvey.getSoyaPrice());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentSoyaPrice", currentSurvey.getApplicationCostCurrency());

            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentLongitude", currentSurvey.getLongitude());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentLatitude", currentSurvey.getLatitude());

            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentCultivarName", currentSurvey.getCultivarName());
            this.setCultivarName(currentSurvey.getCultivarName());

            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentRestResistant", currentSurvey.isRustResistant());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentBT", currentSurvey.isBt());

            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentSporeCollectorPresent", currentSurvey.isSporeCollectorPresent());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentCollectorInstallationDate", currentSurvey.getCollectorInstallationDate());

            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentTotalArea", currentSurvey.getTotalArea());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentTotalPlantedArea", currentSurvey.getTotalPlantedArea());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentPlantPerMeter", currentSurvey.getPlantPerMeter());

            return "/survey/survey/update.xhtml?faces-redirect=true";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Alteração não pode ser iniciada porque a UR não foi encontrada na base de dados!"));
            return "index.xhtml";
        }
    }

    public void showInstallationDatePanel() {
        if (this.getStatusInstallationDatePanel().equals("hidden-sm hidden-md hidden-lg hidden-xs")) {
            this.setStatusInstallationDatePanel("");
        } else {
            this.setStatusInstallationDatePanel("hidden-sm hidden-md hidden-lg hidden-xs");
        }
    }

    public List<String> searchCultivar(String excerpt) {
        return surveyService.searchCultivar(excerpt);
    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        surveyService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "UR";
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doUpdate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
