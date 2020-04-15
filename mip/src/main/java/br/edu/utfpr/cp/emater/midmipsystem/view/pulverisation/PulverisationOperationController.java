package br.edu.utfpr.cp.emater.midmipsystem.view.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.GrowthPhase;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Product;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.PulverisationOperation;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.PulverisationOperationOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Target;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.UseClass;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.ProductUseClassDifferFromTargetException;
import br.edu.utfpr.cp.emater.midmipsystem.service.pulverisation.PulverisationOperationService;
import br.edu.utfpr.cp.emater.midmipsystem.view.AbstractCRUDController;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component(value = "pulverisationOperationController")
@ViewScoped
@RequiredArgsConstructor
public class PulverisationOperationController extends AbstractCRUDController<PulverisationOperation> {

    private final PulverisationOperationService pulverisationOperationService;

    @Getter
    @Setter
    @NotNull(message = "A data da coleta precisa ser informada!")
    private Date sampleDate;

    @Getter
    @Setter
    private Set<PulverisationOperationOccurrence> operationOccurrences;

    @Setter
    @Getter
    private GrowthPhase growthPhase;

    @Setter
    @Getter
    private double caldaVolume;

    @Setter
    @Getter
    private UseClass useClass;

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
    private double productPrice;

    @Setter
    @Getter
    private double productDose;

    @Setter
    @Getter
    private double pulverisationArea;

    @Getter
    @Setter
    private String statusPulverisationAreaPanel = "hidden-sm hidden-md hidden-lg hidden-xs";

    public List<Survey> readAllSurveysUniqueEntries() {
        return pulverisationOperationService.readAllSurveysUniqueEntries();
    }

    @Override
    protected void doCreate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        var surveyIdAsString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currentSurveyId");

        Survey currentSurvey = null;

        try {
            currentSurvey = pulverisationOperationService.readSurveyById(Long.parseLong(surveyIdAsString));

        } catch (EntityNotFoundException | NumberFormatException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Operação de pulverização não pode ser concluída porque a UR não foi encontrada na base de dados!"));
//            return "index.xhtml";
        }

        var newOperation = PulverisationOperation.builder()
                .caldaVolume(this.getCaldaVolume())
                .growthPhase(this.getGrowthPhase())
                .sampleDate(this.getSampleDate())
                .survey(currentSurvey)
                .build();

        if (this.getPulverisationArea() != 0) {
            newOperation.setPulverisationArea(this.getPulverisationArea());
        }

        newOperation.setOperationOccurrences(this.getOperationOccurrences());

        pulverisationOperationService.create(newOperation);
    }

    public String selectTargetSurvey(Long id) {

        try {
            var currentSurvey = pulverisationOperationService.readSurveyById(id);

            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentSurveyFieldName", currentSurvey.getFieldName());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentSurveyHarvestName", currentSurvey.getHarvestName());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentSurveyId", id);

            return "/pulverisation/pulverisation-operation/create-with-survey.xhtml?faces-redirect=true";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Operação de pulverização não pode ser feita porque a UR não foi encontrada na base de dados!"));
            return "index.xhtml";
        }

    }

    public GrowthPhase[] readAllGrowthPhases() {
        return GrowthPhase.values();
    }

    public UseClass[] readAllUseClasses() {
        return UseClass.values();
    }

    public void onUseClassChange() {
        if (this.getUseClass() != null) {
            this.setTargetOptions(pulverisationOperationService.readAllTargetsByUseClass(this.getUseClass()));
            this.setProductOptions(pulverisationOperationService.readAllProductByUseClass(this.getUseClass()));
        }
    }

    public void addOccurrence() throws EntityNotFoundException {

        try {
            var product = pulverisationOperationService.readProductById(this.getProductId());
            var target = pulverisationOperationService.readTargetById(this.getTargetId());

            this.addOperationOccurrence(product, this.getProductPrice(), this.getProductDose(), target);

        } catch (ProductUseClassDifferFromTargetException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public void showPulverisationAreaPanelPanel() {
        if (this.getStatusPulverisationAreaPanel().equals("hidden-sm hidden-md hidden-lg hidden-xs")) {
            this.setStatusPulverisationAreaPanel("");
        } else {
            this.setStatusPulverisationAreaPanel("hidden-sm hidden-md hidden-lg hidden-xs");
        }
    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        pulverisationOperationService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Operação";
    }

    @Override
    public List readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String prepareUpdate(Long anId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @PostConstruct
    public void init() {
        this.operationOccurrences = new HashSet<>();
    }

    public boolean addOperationOccurrence(Product product, double productPrice, double productDose, Target target) throws ProductUseClassDifferFromTargetException {

        if (product.getUseClass() != target.getUseClass()) {
            throw new ProductUseClassDifferFromTargetException();

        } else {
            var occurrence = PulverisationOperationOccurrence.builder().product(product).productPrice(productPrice).dose(productDose).target(target).build();

            var result = this.getOperationOccurrences().add(occurrence);

            return result;
        }

    }

    @Override
    protected void doPrepareUpdate(Long anId) throws EntityNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
