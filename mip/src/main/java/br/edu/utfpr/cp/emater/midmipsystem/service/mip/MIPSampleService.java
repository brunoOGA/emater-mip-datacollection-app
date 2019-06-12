package br.edu.utfpr.cp.emater.midmipsystem.service.mip;

import br.edu.utfpr.cp.emater.midmipsystem.service.survey.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestDisease;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestNaturalPredator;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.SupervisorNotAllowedInCity;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.MIPSampleRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.HarvestRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.SurveyRepository;
import br.edu.utfpr.cp.emater.midmipsystem.service.ICRUDService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class MIPSampleService implements ICRUDService<MIPSample> {

    private final MIPSampleRepository mipSampleRepository;
    private final SurveyService surveyService;
    private final PestService pestService;
    private final PestDiseaseService pestDiseaseService;
    private final PestNaturalPredatorService pestNaturalPredatorService;

    @Autowired
    public MIPSampleService(MIPSampleRepository aMIPSampleRepository,
            SurveyService aSurveyService,
            PestService aPestService,
            PestDiseaseService aPestDiseaseService,
            PestNaturalPredatorService aPestNaturalPredatorService) {

        this.mipSampleRepository = aMIPSampleRepository;
        this.surveyService = aSurveyService;
        this.pestService = aPestService;
        this.pestDiseaseService = aPestDiseaseService;
        this.pestNaturalPredatorService = aPestNaturalPredatorService;
    }

    @Override
    public List<MIPSample> readAll() {
        return List.copyOf(mipSampleRepository.findAll());
    }

    public List<Survey> readAllSurveysUniqueEntries() {
        return List.copyOf(mipSampleRepository.findAll().stream().map(MIPSample::getSurvey).distinct().collect(Collectors.toList()));
    }

    @Override
    public void create(MIPSample aSample) throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {

        if (mipSampleRepository.findAll().stream().anyMatch(currentSample -> currentSample.equals(aSample)))
            throw new EntityAlreadyExistsException();

        var theSurvey = surveyService.readById(aSample.getSurvey().getId());
        
        aSample.setSurvey(theSurvey);
        

        try {
            mipSampleRepository.save(aSample);

        } catch (Exception e) {
            throw new AnyPersistenceException();
            
        }

    }

    @Override
    public MIPSample readById(Long anId) throws EntityNotFoundException {
        return mipSampleRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void update(MIPSample aSample) throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    private final SurveyRepository surveyRepository;
//    private final HarvestService harvestService;
//    private final FieldService fieldService;
//
//    @Autowired
//    public MIPSampleService(SurveyRepository aSurveyRepository, HarvestService aHarvestService, FieldService aFieldService) {
//        this.surveyRepository = aSurveyRepository;
//        this.harvestService = aHarvestService;
//        this.fieldService = aFieldService;
//    }
//
//    public List<Survey> readAll() {
//        return List.copyOf(surveyRepository.findAll());
//    }
//
//    public Harvest readHarvestById(Long id) throws EntityNotFoundException {
//        return harvestService.readById(id);
//    }
//
//    public Survey readById(Long anId) throws EntityNotFoundException {
//        return surveyRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
//    }
//
//    public Field readFieldbyId(Long anId) throws EntityNotFoundException {
//        return fieldService.readById(anId);
//    }
//    
//    public List<Survey> readByHarvestId(Long harvestId) throws EntityNotFoundException {
//        return List.copyOf(surveyRepository.findAll().stream().filter(currentSurvey -> currentSurvey.getHarvestId().equals(harvestId)).collect(Collectors.toList()));
//    }
//
//    public void update(Survey aSurvey) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
//
//        var existentSurvey = surveyRepository.findById(aSurvey.getId()).orElseThrow(EntityNotFoundException::new);
//
//        try {
//            existentSurvey.getQuestionData().setBt(aSurvey.isBt());
//            existentSurvey.getQuestionData().setRustResistant(aSurvey.isRustResistant());
//            
//            existentSurvey.getDateData().setEmergenceDate(aSurvey.getEmergenceDate());
//            existentSurvey.getDateData().setHarvestDate(aSurvey.getHarvestDate());
//            existentSurvey.getDateData().setSowedDate(aSurvey.getSowedDate());
//            
//            existentSurvey.getSizeData().setPlantPerMeter(aSurvey.getPlantPerMeter());
//            existentSurvey.getSizeData().setTotalArea(aSurvey.getTotalArea());
//            existentSurvey.getSizeData().setTotalPlantedArea(aSurvey.getTotalPlantedArea());
//            
//            existentSurvey.getLocationData().setLatitude(aSurvey.getLatitude());
//            existentSurvey.getLocationData().setLongitude(aSurvey.getLongitude());
//            
//            existentSurvey.getProductivityData().setProductivityFarmer(aSurvey.getProductivityFarmer());
//            existentSurvey.getProductivityData().setProductivityField(aSurvey.getProductivityField());
//            existentSurvey.getProductivityData().setSeparatedWeight(aSurvey.isSeparatedWeight());
//            
//            existentSurvey.setSeedName(aSurvey.getSeedName());
//            existentSurvey.setSporeCollectorPresent(aSurvey.isSporeCollectorPresent());
//            
//            surveyRepository.saveAndFlush(existentSurvey);
//
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }
//
//    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
//        
//        var existentHarvest = surveyRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
//        
//        try {
//            surveyRepository.delete(existentHarvest);
//            
//        } catch (DataIntegrityViolationException cve) {
//            throw new EntityInUseException();
//            
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }
//
//    public List<Harvest> readAllHarvests() {
//        return harvestService.readAll();
//    }
//
//    public List<Field> readAllFieldsOutOfCurrentHarvest(Long harvestId) {
//        var fieldsInCurrentHarvest = this.surveyRepository.findAll().stream()
//                .filter(currentSurvey -> currentSurvey.getHarvestId().equals(harvestId))
//                .map(Survey::getField)
//                .collect(Collectors.toList());
//
//        var allFields = this.fieldService.readAll();
//
//        var allFieldsOutOfCurrentHarvest = new ArrayList<Field>(allFields);
//
//        allFieldsOutOfCurrentHarvest.removeAll(fieldsInCurrentHarvest);
//
//        return allFieldsOutOfCurrentHarvest;
//    }
    public Survey readSurveyById(Long id) throws EntityNotFoundException {
        return surveyService.readById(id);
    }

    public List<Pest> readAllPests() {
        return pestService.readAll();
    }

    public List<PestDisease> readAllPestDiseases() {
        return pestDiseaseService.readAll();
    }

    public List<PestNaturalPredator> readAllPestNaturalPredators() {
        return pestNaturalPredatorService.readAll();
    }
}
