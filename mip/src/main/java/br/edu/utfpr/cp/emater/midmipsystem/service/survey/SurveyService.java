package br.edu.utfpr.cp.emater.midmipsystem.service.survey;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.SupervisorNotAllowedInCity;
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
public class SurveyService implements ICRUDService<Survey> {

    private final SurveyRepository surveyRepository;
    private final HarvestService harvestService;
    private final FieldService fieldService;

    @Autowired
    public SurveyService(SurveyRepository aSurveyRepository, HarvestService aHarvestService, FieldService aFieldService) {
        this.surveyRepository = aSurveyRepository;
        this.harvestService = aHarvestService;
        this.fieldService = aFieldService;
    }

    public List<Survey> readAll() {
        return List.copyOf(surveyRepository.findAll());
    }
    
    public Harvest readHarvestById(Long id) throws EntityNotFoundException {
        return harvestService.readById(id);
    }

//    public Harvest readById(Long anId) throws EntityNotFoundException {
//        return harvestRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
//    }
//
    public void create(Survey aSurvey) throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {

        if (surveyRepository.findAll().stream().anyMatch(currentSurvey -> currentSurvey.equals(aSurvey))) 
            throw new EntityAlreadyExistsException();
        
        var theField = fieldService.readById(aSurvey.getFieldId());
        var theHarvest = harvestService.readById(aSurvey.getHarvestId());
        
        aSurvey.setField(theField);
        aSurvey.setHarvest(theHarvest);

        try {
            surveyRepository.save(aSurvey);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }

//    public void update(Harvest aHarvest) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
//
//        var existentHarvest = harvestRepository.findById(aHarvest.getId()).orElseThrow(EntityNotFoundException::new);
//        
//        var allHarvests = harvestRepository.findAll();
//        var allHarvestButThis = new ArrayList<Harvest>(allHarvests);
//        allHarvestButThis.remove(existentHarvest);
//
//        if (allHarvestButThis.stream().anyMatch(currentHarvest -> currentHarvest.equals(aHarvest)))
//            throw new EntityAlreadyExistsException();
//                
//        try {
//            existentHarvest.setName(aHarvest.getName());
//            existentHarvest.setBegin(aHarvest.getBegin());
//            existentHarvest.setEnd(aHarvest.getEnd());
//            
//            harvestRepository.saveAndFlush(existentHarvest);
//
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }
//
//    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
//        
//        var existentHarvest = harvestRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
//        
//        try {
//            harvestRepository.delete(existentHarvest);
//            
//        } catch (DataIntegrityViolationException cve) {
//            throw new EntityInUseException();
//            
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }



    @Override
    public Survey readById(Long anId) throws EntityNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Survey entity) throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Harvest> readAllHarvests() {
        return harvestService.readAll();
    }

    public List<Field> readAllFieldsOutOfCurrentHarvest(Long harvestId) {
        var fieldsInCurrentHarvest = this.surveyRepository.findAll().stream()
                                    .filter(currentSurvey -> currentSurvey.getHarvestId().equals(harvestId))
                                    .map(Survey::getField)
                                    .collect(Collectors.toList());
        
        var allFields = this.fieldService.readAll();
        
        var allFieldsOutOfCurrentHarvest = new ArrayList<Field>(allFields);
        
        allFieldsOutOfCurrentHarvest.removeAll(fieldsInCurrentHarvest);
        
        return allFieldsOutOfCurrentHarvest;
    }
}
