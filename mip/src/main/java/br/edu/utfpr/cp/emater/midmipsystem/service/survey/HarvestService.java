package br.edu.utfpr.cp.emater.midmipsystem.service.survey;

import br.edu.utfpr.cp.emater.midmipsystem.service.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.SupervisorNotAllowedInCity;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.HarvestRepository;
import br.edu.utfpr.cp.emater.midmipsystem.service.ICRUDService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class HarvestService implements ICRUDService<Harvest> {

    private final HarvestRepository harvestRepository;

    @Autowired
    public HarvestService(HarvestRepository aHarvestRepository) {
        this.harvestRepository = aHarvestRepository;
    }

    public List<Harvest> readAll() {
        return List.copyOf(harvestRepository.findAll());
    }
//
//    public MacroRegion readById(Long anId) throws EntityNotFoundException {
//        return macroRegionRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
//    }
//
//    public void create(MacroRegion aMacroRegion) throws EntityAlreadyExistsException, AnyPersistenceException {
//
//        if (macroRegionRepository.findAll().stream().anyMatch(currentMR -> currentMR.equals(aMacroRegion))) {
//            throw new EntityAlreadyExistsException();
//        }
//
//        try {
//            macroRegionRepository.save(aMacroRegion);
//
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }
//
//    public void update(MacroRegion aMacroRegion) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
//
//        MacroRegion existentEntity = macroRegionRepository.findById(aMacroRegion.getId()).orElseThrow(EntityNotFoundException::new);
//
//        if (macroRegionRepository.findAll().stream().anyMatch(currentMR -> currentMR.equals(aMacroRegion)))
//            throw new EntityAlreadyExistsException();
//                
//        try {
//            existentEntity.setName(aMacroRegion.getName());
//            
//            macroRegionRepository.saveAndFlush(existentEntity);
//
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }
//
//    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
//        MacroRegion existentEntity = macroRegionRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
//        
//        try {
//            macroRegionRepository.delete(existentEntity);
//            
//        } catch (DataIntegrityViolationException cve) {
//            throw new EntityInUseException();
//            
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }

    @Override
    public void create(Harvest entity) throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Harvest readById(Long anId) throws EntityNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Harvest entity) throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
