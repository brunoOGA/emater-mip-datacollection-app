package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.FarmerRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.SupervisorRepository;
import br.edu.utfpr.cp.emater.midmipsystem.service.ICRUDService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class SupervisorService implements ICRUDService<Supervisor> {

    private final SupervisorRepository supervisorRepository;

    @Autowired
    public SupervisorService(SupervisorRepository aSupervisorRepository) {
        this.supervisorRepository = aSupervisorRepository;
    }

    @Override
    public List<Supervisor> readAll() {
        return List.copyOf(supervisorRepository.findAll());
    }

//    @Override
//    public Farmer readById(Long anId) throws EntityNotFoundException {
//        return farmerRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
//    }
//
//    public void create(Farmer aFarmer) throws EntityAlreadyExistsException, AnyPersistenceException {
//
//        if (farmerRepository.findAll().stream().anyMatch(currentFarmer -> currentFarmer.equals(aFarmer))) {
//            throw new EntityAlreadyExistsException();
//        }
//
//        try {
//            farmerRepository.save(aFarmer);
//
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }
//
//    public void update(Farmer aFarmer) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
//
//        var existentFarmer = farmerRepository.findById(aFarmer.getId()).orElseThrow(EntityNotFoundException::new);
//
//        if (farmerRepository.findAll().stream().anyMatch(currentFarmer -> currentFarmer.equals(aFarmer)))
//            throw new EntityAlreadyExistsException();
//                
//        try {
//            existentFarmer.setName(aFarmer.getName());
//            
//            farmerRepository.saveAndFlush(existentFarmer);
//
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }
//
//    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
//        
//        var existentFarmer = farmerRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
//        
//        try {
//            farmerRepository.delete(existentFarmer);
//            
//        } catch (DataIntegrityViolationException cve) {
//            throw new EntityInUseException();
//            
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }

    @Override
    public void create(Supervisor entity) throws EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Supervisor readById(Long anId) throws EntityNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Supervisor entity) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
