package br.edu.utfpr.cp.emater.midmipsystem.domain.base.region;

import br.edu.utfpr.cp.emater.midmipsystem.domain.base.macroRegion.*;
import br.edu.utfpr.cp.emater.midmipsystem.library.ICRUDService;
import br.edu.utfpr.cp.emater.midmipsystem.library.dtos.base.MacroRegionDTO;
import br.edu.utfpr.cp.emater.midmipsystem.library.dtos.base.RegionDTO;
import br.edu.utfpr.cp.emater.midmipsystem.library.exceptions.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.library.exceptions.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.library.exceptions.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegionService implements ICRUDService<RegionDTO> {

    private final RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository aRegionRepository) {
        this.regionRepository = aRegionRepository;
    }

    @Override
    public List<RegionDTO> readAll() {
        List<RegionDTO> result = new ArrayList<>();

        for (Region currentRegion : regionRepository.findAll()) {
            result.add(this.convertToDTO(currentRegion));
        }

        return result;
    }

//    public MacroRegionDTO readById(Long id) throws EntityNotFoundException {
//        MacroRegion entity = macroRegionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
//
//        return this.convertToDTO(entity);
//    }
//
//    public void create(MacroRegionDTO aMacroRegionDTO) throws EntityAlreadyExistsException, AnyPersistenceException {
//
//        MacroRegion persistentEntity = this.prepareEntityForPersistence(aMacroRegionDTO);
//
//        if (macroRegionRepository.findAll().stream().anyMatch(currentMR -> currentMR.equals(persistentEntity))) {
//            throw new EntityAlreadyExistsException();
//        }
//
//        try {
//            macroRegionRepository.save(persistentEntity);
//
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }
//
//    private MacroRegion prepareEntityForPersistence(MacroRegionDTO aDTO) {
//        return MacroRegion.builder().name(aDTO.getName()).build();
//    }
//
    private RegionDTO convertToDTO(Region aRegion) {
        return RegionDTO.builder()
                .id(aRegion.getId())
                .name(aRegion.getName())
                .macroRegion(aRegion.getMacroRegion().getName())
                .cities(aRegion.getCities().stream().map(City::getName).collect(Collectors.toSet()))
                .build();
    }
//
//    private void updateMacroRegionAttributes(MacroRegion original, MacroRegionDTO updated) {
//        original.setName(updated.getName());
//    }
//
//    public void update(MacroRegionDTO aMacroRegionDTO) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
//
//        MacroRegion existentEntity = macroRegionRepository.findById(aMacroRegionDTO.getId()).orElseThrow(EntityNotFoundException::new);
//        MacroRegion aNewEntityJustForEqualsComparison = MacroRegion.builder().name(aMacroRegionDTO.getName()).build();
//
//        if (macroRegionRepository.findAll().stream().anyMatch(currentMR -> currentMR.equals(aNewEntityJustForEqualsComparison)))
//            throw new EntityAlreadyExistsException();
//        
//        this.updateMacroRegionAttributes(existentEntity, aMacroRegionDTO);
//        
//        try {
//            macroRegionRepository.saveAndFlush(existentEntity);
//
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }
//
//    public void delete(Long anId) throws EntityNotFoundException {
//        MacroRegion existentEntity = macroRegionRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
//        
//        try {
//            macroRegionRepository.delete(existentEntity);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
