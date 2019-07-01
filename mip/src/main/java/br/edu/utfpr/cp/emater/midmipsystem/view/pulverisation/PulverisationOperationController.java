package br.edu.utfpr.cp.emater.midmipsystem.view.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.view.mip.*;
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
import br.edu.utfpr.cp.emater.midmipsystem.service.pulverisation.PulverisationOperationService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component(value = "pulverisationOperationController")
@RequestScope
public class PulverisationOperationController extends MIPSample {

    private final PulverisationOperationService pulverisationOperationService;

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
    public PulverisationOperationController(PulverisationOperationService aPulverisationOperationService) {
        this.pulverisationOperationService = aPulverisationOperationService;

        this.populatePestOccurrences();
        this.populatePestDiseaseOccurrences();
        this.populateNaturalPredatorOccurrences();
    }

    private void populatePestOccurrences() {
        pestOccurrences = new ArrayList<>();

        pulverisationOperationService.readAllPests().forEach(currentPest
                -> pestOccurrences.add(MIPSamplePestOccurrence.builder().pest(currentPest).value(0.0).build())
        );
    }

    private void populatePestDiseaseOccurrences() {
        pestDiseaseOccurrences = new ArrayList<>();

        pulverisationOperationService.readAllPestDiseases().forEach(currentPestDisease
                -> pestDiseaseOccurrences.add(MIPSamplePestDiseaseOccurrence.builder().pestDisease(currentPestDisease).value(0.0).build())
        );
    }

    private void populateNaturalPredatorOccurrences() {
        naturalPredatorOccurrences = new ArrayList<>();

        pulverisationOperationService.readAllPestNaturalPredators().forEach(currentNaturalPredator
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
        return pulverisationOperationService.readAll();
    }

    public List<Survey> readAllSurveysUniqueEntries() {
        return pulverisationOperationService.readAllSurveysUniqueEntries();
    }

    public String create() {
        
        Survey currentSurvey = null;

        try {
            currentSurvey = pulverisationOperationService.readSurveyById(this.getCurrentSurveyId());

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Anotação de campo não pode ser feita porque a UR não foi encontrada na base de dados!"));
            return "index.xhtml";
        }
        
        var newSample = MIPSample.builder()
                                    .defoliation(this.getDefoliation())
                                    .growthPhase(this.getGrowthPhase())
                                    .sampleDate(this.getSampleDate())
                                    .survey(currentSurvey)
                                    .build();
        
        this.trimOccurrencesForSample(newSample);

        try {
            pulverisationOperationService.create(newSample);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Anotação de campo criada com sucesso!"));
            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe uma anotação de campo com essa data para essa UR! Use datas diferentes."));
            return "create.xhtml";

        } catch (EntityNotFoundException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Anotação de campo não pode ser feita porque a UR não foi encontrada na base de dados!"));
            return "index.xhtml";
            
        } catch (AnyPersistenceException | SupervisorNotAllowedInCity e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }
    }

    public String delete(Long aSampleId) {

        try {
            pulverisationOperationService.delete(aSampleId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Amostra excluída!"));
            return "index.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Amostra não pode ser excluída porque não foi encontrada na base de dados!"));
            return "index.xhtml";

        } catch (EntityInUseException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Amostra não pode ser excluída porque está sendo usada no sistema!"));
            return "index.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }
    }

    public String selectTargetSurvey(Long id) {

        Survey currentSurvey = null;

        try {
            currentSurvey = pulverisationOperationService.readSurveyById(id);
            this.setCurrentSurveyId(id);
            this.setCurrentSurveyFieldName(currentSurvey.getFieldName());
            this.setCurrentSurveyHarvestName(currentSurvey.getHarvestName());

            return "/mip/mip-sample/create-with-survey.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Anotação de campo não pode ser feita porque a UR não foi encontrada na base de dados!"));
            return "index.xhtml";
        }

    }

    public GrowthPhase[] readAllGrowthPhases() {
        return GrowthPhase.values();
    }
}
