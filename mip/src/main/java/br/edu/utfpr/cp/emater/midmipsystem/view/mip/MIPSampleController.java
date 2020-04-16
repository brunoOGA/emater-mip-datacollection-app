package br.edu.utfpr.cp.emater.midmipsystem.view.mip;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.GrowthPhase;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSampleNaturalPredatorOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSamplePestDiseaseOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSamplePestOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.SupervisorNotAllowedInCity;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import br.edu.utfpr.cp.emater.midmipsystem.view.AbstractCRUDController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component(value = "mipSampleController")
@ViewScoped
public class MIPSampleController extends AbstractCRUDController<MIPSample> {

    private final MIPSampleService mipSampleService;

    @Setter
    @Getter
    private Date sampleDate;

    @Setter
    @Getter
    private int defoliation;

    @Setter
    @Getter
    private GrowthPhase growthPhase;

    @Setter
    @Getter
    private Long currentSurveyId;

    @Setter
    @Getter
    private String currentSurveyFieldName;

    @Setter
    @Getter
    private String currentSurveyHarvestName;

    @Setter
    @Getter
    private List<MIPSamplePestOccurrence> pestOccurrences;

    @Setter
    @Getter
    private List<MIPSamplePestDiseaseOccurrence> pestDiseaseOccurrences;

    @Setter
    @Getter
    private List<MIPSampleNaturalPredatorOccurrence> naturalPredatorOccurrences;

    @Autowired
    public MIPSampleController(MIPSampleService aMIPSampleService) {
        this.mipSampleService = aMIPSampleService;

        this.populatePestOccurrences();
        this.populatePestDiseaseOccurrences();
        this.populateNaturalPredatorOccurrences();
    }

    private void populatePestOccurrences() {
        pestOccurrences = new ArrayList<>();

        mipSampleService.readAllPests().forEach(currentPest
                -> pestOccurrences.add(MIPSamplePestOccurrence.builder().pest(currentPest).value(0.0).build())
        );
    }

    private void populatePestDiseaseOccurrences() {
        pestDiseaseOccurrences = new ArrayList<>();

        mipSampleService.readAllPestDiseases().forEach(currentPestDisease
                -> pestDiseaseOccurrences.add(MIPSamplePestDiseaseOccurrence.builder().pestDisease(currentPestDisease).value(0.0).build())
        );
    }

    private void populateNaturalPredatorOccurrences() {
        naturalPredatorOccurrences = new ArrayList<>();

        mipSampleService.readAllPestNaturalPredators().forEach(currentNaturalPredator
                -> naturalPredatorOccurrences.add(MIPSampleNaturalPredatorOccurrence.builder().pestNaturalPredator(currentNaturalPredator).value(0.0).build())
        );
    }

    private void trimOccurrencesForSample(MIPSample aSample) {

        aSample.setMipSamplePestOccurrence(
                pestOccurrences.stream().filter(currentPest -> currentPest.getValue() != 0).collect(Collectors.toSet())
        );

        aSample.setMipSamplePestDiseaseOccurrence(
                pestDiseaseOccurrences.stream().filter(currentPestDisease -> currentPestDisease.getValue() != 0).collect(Collectors.toSet())
        );

        aSample.setMipSampleNaturalPredatorOccurrence(
                naturalPredatorOccurrences.stream().filter(currentNaturalPredator -> currentNaturalPredator.getValue() != 0).collect(Collectors.toSet())
        );
    }

    public List<MIPSample> readAll() {
        return mipSampleService.readAll();
    }

    public List<Survey> readAllSurveysUniqueEntries() {
        return mipSampleService.readAllSurveysUniqueEntries();
    }

    @Override
    protected void doCreate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {

        var surveyIdAsString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currentSurveyId");

        Survey currentSurvey = null;

        try {
            currentSurvey = mipSampleService.readSurveyById(Long.parseLong(surveyIdAsString));

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Anotação de campo não pode ser feita porque a UR não foi encontrada na base de dados!"));
//            return "index.xhtml";
        }

        var newSample = MIPSample.builder()
                .defoliation(this.getDefoliation())
                .growthPhase(this.getGrowthPhase())
                .sampleDate(this.getSampleDate())
                .survey(currentSurvey)
                .build();

        this.trimOccurrencesForSample(newSample);

        mipSampleService.create(newSample);
    }

    public String selectTargetSurvey(Long id) {

        try {
            var currentSurvey = mipSampleService.readSurveyById(id);

            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentSurveyFieldName", currentSurvey.getFieldName());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentSurveyHarvestName", currentSurvey.getHarvestName());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentSurveyId", id);

            return "/mip/mip-sample/create-with-survey.xhtml?faces-redirect=true";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Anotação de campo não pode ser feita porque a UR não foi encontrada na base de dados!"));
            return "index.xhtml";
        }

    }

    public GrowthPhase[] readAllGrowthPhases() {
        return GrowthPhase.values();
    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        mipSampleService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Amostra";
    }

    @Override
    public String prepareUpdate(Long anId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doPrepareUpdate(Long anId) throws EntityNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doUpdate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
