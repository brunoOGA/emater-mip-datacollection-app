package br.edu.utfpr.cp.emater.midmipsystem.service.mip;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestDisease;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestNaturalPredator;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.SupervisorNotAllowedInCity;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestDiseaseRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestNaturalPredatorRepository;
import br.edu.utfpr.cp.emater.midmipsystem.service.ICRUDService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class PestNaturalPredatorService implements ICRUDService<PestNaturalPredator> {

    private final PestNaturalPredatorRepository pestNaturalPredatorRepository;

    @Autowired
    public PestNaturalPredatorService(PestNaturalPredatorRepository aPestNaturalPredatorRepository) {
        this.pestNaturalPredatorRepository = aPestNaturalPredatorRepository;
    }

    @Override
    public List<PestNaturalPredator> readAll() {
        return List.copyOf(pestNaturalPredatorRepository.findAll());
    }

//    @Override
//    public PestDisease readById(Long anId) throws EntityNotFoundException {
//        return pestDiseaseRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
//    }
//
//    public void create(PestDisease aPestDisease) throws EntityAlreadyExistsException, AnyPersistenceException {
//
//        if (pestDiseaseRepository.findAll().stream().anyMatch(currentPestDisease -> currentPestDisease.equals(aPestDisease))) {
//            throw new EntityAlreadyExistsException();
//        }
//
//        try {
//            pestDiseaseRepository.save(aPestDisease);
//
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }
//
//    public void update(PestDisease aPestDisease) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException { 
//        
//        var existentPestDisease = pestDiseaseRepository.findById(aPestDisease.getId()).orElseThrow(EntityNotFoundException::new);
//        
//        var allPestDiseasesWithoutExistentPest = new ArrayList<PestDisease>(pestDiseaseRepository.findAll());
//        allPestDiseasesWithoutExistentPest.remove(existentPestDisease);
//
//        if (allPestDiseasesWithoutExistentPest.stream().anyMatch(currentPestDisease -> currentPestDisease.equals(aPestDisease)))
//            throw new EntityAlreadyExistsException();
//                
//        try {
//            existentPestDisease.setUsualName(aPestDisease.getUsualName());
//                        
//            pestDiseaseRepository.saveAndFlush(existentPestDisease);
//
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }
//
//    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
//        
//        var existentPestDisease = pestDiseaseRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
//        
//        try {
//            pestDiseaseRepository.delete(existentPestDisease);
//            
//        } catch (DataIntegrityViolationException cve) {
//            throw new EntityInUseException();
//            
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }

    @Override
    public void create(PestNaturalPredator entity) throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PestNaturalPredator readById(Long anId) throws EntityNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(PestNaturalPredator entity) throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
