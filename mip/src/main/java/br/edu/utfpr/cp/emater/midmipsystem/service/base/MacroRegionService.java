package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.service.base.MacroRegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.library.dtos.base.MacroRegionDTO;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MacroRegionService {

    private final MacroRegionRepository macroRegionRepository;

    @Autowired
    public MacroRegionService(MacroRegionRepository macroRegionRepository) {
        this.macroRegionRepository = macroRegionRepository;
    }

    public List<MacroRegionDTO> readAll() {
        List<MacroRegionDTO> result = new ArrayList<>();

        for (MacroRegion currentMR : macroRegionRepository.findAll()) {
            result.add(this.convertToDTO(currentMR));
        }

        return result;
    }

    public MacroRegionDTO readById(Long id) throws EntityNotFoundException {
        MacroRegion entity = macroRegionRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return this.convertToDTO(entity);
    }

    public void create(MacroRegionDTO aMacroRegionDTO) throws EntityAlreadyExistsException, AnyPersistenceException {

        MacroRegion persistentEntity = this.prepareEntityForPersistence(aMacroRegionDTO);

        if (macroRegionRepository.findAll().stream().anyMatch(currentMR -> currentMR.equals(persistentEntity))) {
            throw new EntityAlreadyExistsException();
        }

        try {
            macroRegionRepository.save(persistentEntity);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }

    private MacroRegion prepareEntityForPersistence(MacroRegionDTO aDTO) {
        return MacroRegion.builder().name(aDTO.getName()).build();
    }

    private MacroRegionDTO convertToDTO(MacroRegion aMacroRegion) {
        return MacroRegionDTO.builder()
                .id(aMacroRegion.getId())
                .name(aMacroRegion.getName())
                .build();
    }

    private void updateMacroRegionAttributes(MacroRegion original, MacroRegionDTO updated) {
        original.setName(updated.getName());
    }

    public void update(MacroRegionDTO aMacroRegionDTO) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {

        MacroRegion existentEntity = macroRegionRepository.findById(aMacroRegionDTO.getId()).orElseThrow(EntityNotFoundException::new);
        MacroRegion aNewEntityJustForEqualsComparison = MacroRegion.builder().name(aMacroRegionDTO.getName()).build();

        if (macroRegionRepository.findAll().stream().anyMatch(currentMR -> currentMR.equals(aNewEntityJustForEqualsComparison)))
            throw new EntityAlreadyExistsException();
        
        this.updateMacroRegionAttributes(existentEntity, aMacroRegionDTO);
        
        try {
            macroRegionRepository.saveAndFlush(existentEntity);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }

    public void delete(Long anId) throws EntityNotFoundException {
        MacroRegion existentEntity = macroRegionRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
        
        try {
            macroRegionRepository.delete(existentEntity);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
