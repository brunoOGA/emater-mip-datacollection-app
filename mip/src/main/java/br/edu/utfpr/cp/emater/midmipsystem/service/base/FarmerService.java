package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.FarmerRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.FieldRepository;
import br.edu.utfpr.cp.emater.midmipsystem.service.ICRUDService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FarmerService implements ICRUDService<Farmer> {

    private final FarmerRepository farmerRepository;

    private final FieldRepository fieldRepository;

    @Override
    public List<Farmer> readAll() {
        return List.copyOf(farmerRepository.findAll());
    }

    @Override
    public Farmer readById(Long anId) throws EntityNotFoundException {
        return farmerRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
    }

    public void create(Farmer aFarmer) throws EntityAlreadyExistsException, AnyPersistenceException {

        if (farmerRepository.findAll().stream().anyMatch(currentFarmer -> currentFarmer.equals(aFarmer))) {
            throw new EntityAlreadyExistsException();
        }

        try {
            farmerRepository.save(aFarmer);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }

    public void update(Farmer aFarmer) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {

        var existentFarmer = farmerRepository.findById(aFarmer.getId()).orElseThrow(EntityNotFoundException::new);

        if (farmerRepository.findAll().stream().anyMatch(currentFarmer -> currentFarmer.equals(aFarmer))) {
            throw new EntityAlreadyExistsException();
        }

        try {
            existentFarmer.setName(aFarmer.getName());

            farmerRepository.saveAndFlush(existentFarmer);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }

    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException, AccessDeniedException {

        var existentFarmer = farmerRepository.findById(anId).orElseThrow(EntityNotFoundException::new);

        try {
            farmerRepository.delete(existentFarmer);

        } catch (AccessDeniedException cve) {
            throw cve;

        } catch (DataIntegrityViolationException cve) {
            throw new EntityInUseException();

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }
}
