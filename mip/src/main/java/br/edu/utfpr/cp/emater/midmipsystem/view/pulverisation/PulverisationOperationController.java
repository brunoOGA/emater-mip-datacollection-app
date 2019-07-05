package br.edu.utfpr.cp.emater.midmipsystem.view.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.view.mip.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.GrowthPhase;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSampleNaturalPredatorOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSamplePestDiseaseOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSamplePestOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Product;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.PulverisationOperation;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Target;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.TargetCategory;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.SupervisorNotAllowedInCity;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import br.edu.utfpr.cp.emater.midmipsystem.service.pulverisation.PulverisationOperationService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component(value = "pulverisationOperationController")
//@RequestScope
@ViewScoped
public class PulverisationOperationController extends PulverisationOperation {

    private final PulverisationOperationService pulverisationOperationService;

    @Setter
    @Getter    
    private TargetCategory targetCategory;
    
    @Setter
    @Getter
    private Long targetId;
    
    @Setter
    @Getter        
    private List<Target> targetOptions;
    
    @Setter
    @Getter       
    private List<Product> productOptions;
    
    @Setter
    @Getter       
    private Long productId;
    
    @Setter
    @Getter       
    private Long surveyId;

    @Setter
    @Getter    
    private double productPrice;
    
    @Autowired
    public PulverisationOperationController(PulverisationOperationService aPulverisationOperationService) {
        this.pulverisationOperationService = aPulverisationOperationService;

    }

    public List<MIPSample> readAll() {
        return pulverisationOperationService.readAll();
    }

    public List<Survey> readAllSurveysUniqueEntries() {
        return pulverisationOperationService.readAllSurveysUniqueEntries();
    }

    public String create() {
        
        var surveyIdAsString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currentSurveyId");
        System.out.println("ID: " + surveyIdAsString);
        
//        Survey currentSurvey = null;
//
//        try {
//            currentSurvey = pulverisationOperationService.readSurveyById(this.getCurrentSurveyId());
//
//        } catch (EntityNotFoundException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Anotação de campo não pode ser feita porque a UR não foi encontrada na base de dados!"));
//            return "index.xhtml";
//        }
//        
//        var newSample = MIPSample.builder()
//                                    .defoliation(this.getDefoliation())
//                                    .growthPhase(this.getGrowthPhase())
//                                    .sampleDate(this.getSampleDate())
//                                    .survey(currentSurvey)
//                                    .build();
//        
//        this.trimOccurrencesForSample(newSample);
//
//        try {
//            pulverisationOperationService.create(newSample);
//
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Anotação de campo criada com sucesso!"));
//            return "index.xhtml";
//
//        } catch (EntityAlreadyExistsException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe uma anotação de campo com essa data para essa UR! Use datas diferentes."));
//            return "create.xhtml";
//
//        } catch (EntityNotFoundException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Anotação de campo não pode ser feita porque a UR não foi encontrada na base de dados!"));
//            return "index.xhtml";
//            
//        } catch (AnyPersistenceException | SupervisorNotAllowedInCity e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
//        }
    }

    public String delete(Long anOperationId) {

        try {
            pulverisationOperationService.delete(anOperationId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Operação excluída!"));
            return "index.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Operação não pode ser excluída porque não foi encontrada na base de dados!"));
            return "index.xhtml";

        } catch (EntityInUseException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Operação não pode ser excluída porque está sendo usada no sistema!"));
            return "index.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }
    }

    public String selectTargetSurvey(Long id) {

        try {
            var currentSurvey = pulverisationOperationService.readSurveyById(id);
            
            var paramValues = String.format("?currentSurveyFieldName=%s&currentSurveyHarvestName=%s&currentSurveyId=%d&faces-redirect=true", currentSurvey.getFieldName(), currentSurvey.getHarvestName(), id);

            return "/pulverisation/pulverisation-operation/create-with-survey.xhtml" + paramValues;

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Operação de pulverização não pode ser feita porque a UR não foi encontrada na base de dados!"));
            return "index.xhtml";
        }

    }

    public GrowthPhase[] readAllGrowthPhases() {
        return GrowthPhase.values();
    }
    
    public TargetCategory[] readAllTargetCategories() {
        return TargetCategory.values();
    }
    
    public void onTargetCategoryChange() {
        if (this.getTargetCategory() != null)
            this.setTargetOptions(pulverisationOperationService.readAllTargetsByCategory(this.getTargetCategory()));
    }

    public void onTargetChange() {
        if (this.getTargetId() != null)
            this.setProductOptions(pulverisationOperationService.readAllProductByTarget(this.getTargetId()));
    }
    
    public void addOccurrence() throws EntityNotFoundException {
        var product = pulverisationOperationService.readProductById (this.getProductId());
        var target = pulverisationOperationService.readTargetById (this.getTargetId());
        
        this.addOperationOccurrence(product, this.getProductPrice(), target);
    }
    
}
