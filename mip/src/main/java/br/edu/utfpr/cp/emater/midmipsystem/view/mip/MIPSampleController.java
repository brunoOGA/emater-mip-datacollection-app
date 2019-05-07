package br.edu.utfpr.cp.emater.midmipsystem.view.mip;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.GrowthPhase;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSampleNaturalPredatorOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSamplePestDiseaseOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSamplePestOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestDisease;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestNaturalPredator;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestSize;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.view.ICRUDController;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.PestService;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component(value = "mipSampleController")
@RequestScope
@Log
public class MIPSampleController extends MIPSample implements ICRUDController<MIPSample> {

    private final MIPSampleService mipSampleService;

    @Getter
    @Setter
    private Long selectedHarvestId;

    @Getter
    @Setter
    private List<MIPSamplePestOccurrence> pestOccurrences;

    @Getter
    @Setter
    private List<MIPSamplePestDiseaseOccurrence> pestDiseaseOccurrences;

    @Getter
    @Setter
    private List<MIPSampleNaturalPredatorOccurrence> pestNaturalPredatorOccurrences;

    @Autowired
    public MIPSampleController(MIPSampleService aMipSampleService) {
        this.mipSampleService = aMipSampleService;

        this.prepareMIPSamplePestOccurrences();
        this.prepareMIPSamplePestDiseaseOccurrences();
        this.prepareMIPSampleNaturalPredatorOccurrences();

    }

    @Override
    public List<MIPSample> readAll() {
        return mipSampleService.readAll();
    }

    public List<Survey> readAllSurveysThatHasSample() {
        return mipSampleService.readAllSurveysThatHasSample();
    }

    public List<Harvest> readAllHarvests() {
        return mipSampleService.readAllHarvests();
    }

    public List<Survey> readAllSurveysInSelectedHarvest() {
        return List.copyOf(mipSampleService.readAllSurveys().stream().filter(currentSurvey -> currentSurvey.getHarvestId().equals(this.getSelectedHarvestId())).collect(Collectors.toList()));
    }

    public void selectHarvest() {

        try {
            var selectedHarvest = mipSampleService.readHarvestById(this.getSelectedHarvestId());

            this.setSelectedHarvestId(selectedHarvest.getId());
            log.info(String.format("selectHarvestId: %d", this.selectedHarvestId));

//            return "create.xhtml";

        } catch (EntityNotFoundException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Safra não pode ser selecionada porque não foi encontrada na base de dados!"));
//            return "index.xhtml";
        }
}

private void prepareMIPSamplePestOccurrences() {
        var pestOccurrences = new ArrayList<MIPSamplePestOccurrence>();

        for (Pest currentPest : mipSampleService.readAllPests()) {
            pestOccurrences.add(MIPSamplePestOccurrence.builder().pest(currentPest).value(0.0).build());
        }

        this.setPestOccurrences(pestOccurrences);
    }

    private void prepareMIPSamplePestDiseaseOccurrences() {
        var pestDiseaseOccurrences = new ArrayList<MIPSamplePestDiseaseOccurrence>();

        for (PestDisease currentPestDisease : mipSampleService.readAllPestDiseases()) {
            pestDiseaseOccurrences.add(MIPSamplePestDiseaseOccurrence.builder().pestDisease(currentPestDisease).value(0.0).build());
        }

        this.setPestDiseaseOccurrences(pestDiseaseOccurrences);
    }

    private void prepareMIPSampleNaturalPredatorOccurrences() {
        var pestNaturalPredatorOccurrences = new ArrayList<MIPSampleNaturalPredatorOccurrence>();

        for (PestNaturalPredator currentNaturalPredator : mipSampleService.readAllPestNaturalPredators()) {
            pestNaturalPredatorOccurrences.add(MIPSampleNaturalPredatorOccurrence.builder().pestNaturalPredator(currentNaturalPredator).value(0.0).build());
        }

        this.setPestNaturalPredatorOccurrences(pestNaturalPredatorOccurrences);
    }

    public List<GrowthPhase> readAllGrowthPhases() {
        return Arrays.asList(GrowthPhase.values());
    }

    public List<Pest> readAllPests() {
        return mipSampleService.readAllPests();
    }

    @Override
        public String create() {

//        this.getPestOccurrences().stream().filter(current -> current.getValue() != 0).forEach(current -> System.out.println(current.getPestUsualName() + ": " + current.getValue()));
//        this.getPestDiseaseOccurrences().stream().filter(current -> current.getValue() != 0).forEach(current -> System.out.println(current.getPestDiseaseUsualName() + ": " + current.getValue()));
//        this.getPestNaturalPredatorOccurrences().stream().filter(current -> current.getValue() != 0).forEach(current -> System.out.println(current.getPestNaturalPredatorUsualName() + ": " + current.getValue()));

        log.info(String.format("Survey id: %d, Survey obj: %s", this.getSurvey().getId(), this.getSurvey().toString()));
        log.info(String.format("Harvest ID: %d", this.getSelectedHarvestId()));
        
        return "index.xhtml";

//        var newPest = Pest.builder().usualName(this.getUsualName()).scientificName(this.getScientificName()).pestSize(this.getPestSize()).build();
//
//        try {
//            pestService.create(newPest);
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", String.format("Inseto Praga [%s] criado com sucesso!", this.getUsualName())));
//            return "index.xhtml";
//
//        } catch (EntityAlreadyExistsException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um inseto praga com esse nome e tamanho! Use um nome/tamanho diferente."));
//            return "create.xhtml";
//
//        } catch (AnyPersistenceException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
//            return "index.xhtml";
//        }
    }
//
//    @Override
//    public String prepareUpdate(Long anId) {
//
//        try {
//            Pest existentPest = pestService.readById(anId);
//            this.setId(existentPest.getId());
//            this.setUsualName(existentPest.getUsualName());
//            this.setScientificName(existentPest.getScientificName());
//            this.setPestSize(existentPest.getPestSize());
//            
//            return "update.xhtml";
//
//        } catch (EntityNotFoundException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Inseto praga não pode ser alterado porque não foi encontrado na base de dados!"));
//            return "index.xhtml";
//        }
//    }
//
//    @Override
//    public String update() {
//        var updatedPest = Pest.builder().id(this.getId()).usualName(this.getUsualName()).scientificName(this.getScientificName()).pestSize(this.getPestSize()).build();
//
//        try {
//            pestService.update(updatedPest);
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Inseto praga alterado"));
//            return "index.xhtml";
//
//        } catch (EntityAlreadyExistsException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um inseto praga com esse nome/tamanho! Use um nome/tamanho diferente."));
//            return "update.xhtml";
//
//        } catch (EntityNotFoundException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Inseto praga não pode ser alterado porque não foi encontrado na base de dados!"));
//            return "update.xhtml";
//
//        } catch (AnyPersistenceException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
//            return "index.xhtml";
//        }
//
//    }
//
//    @Override
//    public String prepareDelete(Long anId) {
//
//        try {
//            var existentPest = pestService.readById(anId);
//            
//            this.setId(existentPest.getId());
//            this.setUsualName(existentPest.getUsualName());
//            this.setPestSize(existentPest.getPestSize());
//
//            return "delete.xhtml";
//
//        } catch (EntityNotFoundException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Inseto praga não pode ser excluído porque não foi encontrado na base de dados!"));
//            return "index.xhtml";
//        }
//    }
//
//    @Override
//    public String delete() {
//        
//        try {
//            pestService.delete(this.getId());
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Inseto praga excluído!"));
//            return "index.xhtml";
//
//        } catch (EntityNotFoundException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Inseto praga não pode ser excluído porque não foi encontrado na base de dados!"));
//            return "index.xhtml";
//            
//        } catch (EntityInUseException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Inseto praga não pode ser excluído porque está sendo usado em uma amostra!"));
//            return "index.xhtml";
//            
//        } catch (AnyPersistenceException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
//            return "index.xhtml";
//        }
//    }

    @Override
        public String prepareUpdate(Long anId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
        public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
        public String prepareDelete(Long anId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
        public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
