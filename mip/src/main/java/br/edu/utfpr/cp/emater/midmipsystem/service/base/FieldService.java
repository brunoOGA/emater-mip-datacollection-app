package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.FarmerRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.FieldRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.SupervisorRepository;
import br.edu.utfpr.cp.emater.midmipsystem.service.ICRUDService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class FieldService implements ICRUDService<Field> {

    private final FieldRepository fieldRepository;

    @Autowired
    public FieldService(FieldRepository aFieldRepository) {
        this.fieldRepository = aFieldRepository;
    }

    @Override
    public List<Field> readAll() {
        return List.copyOf(fieldRepository.findAll());
    }
    
//    public List<Region> readAllRegions() {
//        return this.regionService.readAll();
//    }
//
//    @Override
//    public Supervisor readById(Long anId) throws EntityNotFoundException {
//        return supervisorRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
//    }
//
//    public void create(Supervisor aSupervisor) throws EntityAlreadyExistsException, AnyPersistenceException {
//
//        if (supervisorRepository.findAll().stream().anyMatch(currentSupervisor -> currentSupervisor.equals(aSupervisor))) {
//            throw new EntityAlreadyExistsException();
//        }
//
//        try {
//            supervisorRepository.save(aSupervisor);
//
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }
//
//    public void update(Supervisor aSupervisor) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException { 
//        
//        var existentSupervisor = supervisorRepository.findById(aSupervisor.getId()).orElseThrow(EntityNotFoundException::new);
//        
//        var allSupervisorsWithoutExistentSupervisor = new ArrayList<Supervisor>(supervisorRepository.findAll());
//        allSupervisorsWithoutExistentSupervisor.remove(existentSupervisor);
//
//        if (allSupervisorsWithoutExistentSupervisor.stream().anyMatch(currentSupervisor -> currentSupervisor.equals(aSupervisor)))
//            throw new EntityAlreadyExistsException();
//                
//        try {
//            existentSupervisor.setName(aSupervisor.getName());
//            existentSupervisor.setEmail(aSupervisor.getEmail());
//            
//            var theRegion = regionService.readById(aSupervisor.getRegionId());
//            existentSupervisor.setRegion(theRegion);
//            
//            supervisorRepository.saveAndFlush(existentSupervisor);
//
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }
//
//    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
//        
//        var existentSupervisor = supervisorRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
//        
//        try {
//            supervisorRepository.delete(existentSupervisor);
//            
//        } catch (DataIntegrityViolationException cve) {
//            throw new EntityInUseException();
//            
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }

    @Override
    public void create(Field entity) throws EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Field readById(Long anId) throws EntityNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Field entity) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
