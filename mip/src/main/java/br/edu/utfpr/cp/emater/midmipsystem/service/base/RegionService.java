package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.RegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.ICRUDService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegionService implements ICRUDService<Region> {

    private final RegionRepository regionRepository;
    private final MacroRegionService macroRegionService;
    private final CityService cityService;

    @Autowired
    public RegionService(RegionRepository aRegionRepository, MacroRegionService aMacroRegionService, CityService aCityService) {
        this.regionRepository = aRegionRepository;
        this.macroRegionService = aMacroRegionService;
        this.cityService = aCityService;
    }

    @Override
    public List<Region> readAll() {
        return List.copyOf(regionRepository.findAll());
    }
    
    public List<MacroRegion> readAllMacroRegions() {
        return this.macroRegionService.readAll();
    }
    
    public List<City> readAllCities() {
        var allCities = this.cityService.readAll();
        var citiesWithinARegion = this.readAll().stream().flatMap(currentRegion -> currentRegion.getCities().stream()).collect(Collectors.toList());
        
        var citiesWithoutRegion = new ArrayList<City> (allCities);
        citiesWithoutRegion.removeAll(citiesWithinARegion);
        
        return citiesWithoutRegion;
    }

//    public MacroRegionDTO readById(Long id) throws EntityNotFoundException {
//        MacroRegion entity = macroRegionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
//
//        return this.convertToDTO(entity);
//    }
//
    
    private Set<City> retrieveCities (Set<Long> ids) throws EntityNotFoundException {
        var result = new HashSet<City>();
        
        for (Long id: ids)
            result.add(cityService.readById(id));
        
        return result;
    }
    
    public void create(Region aRegion) throws EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {

        var theMacroRegion = macroRegionService.readById(aRegion.getId());    
        var theCities = retrieveCities(aRegion.getCities().stream().map(City::getId).collect(Collectors.toSet()));
        
        var newRegion = Region.builder().name(aRegion.getName()).macroRegion(theMacroRegion).build();
        newRegion.setCities(theCities);

        if (regionRepository.findAll().stream().anyMatch(currentRegion -> currentRegion.equals(newRegion)))
            throw new EntityAlreadyExistsException();

        try {
            regionRepository.save(newRegion);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }
//
//    private MacroRegion prepareEntityForPersistence(MacroRegionDTO aDTO) {
//        return MacroRegion.builder().name(aDTO.getName()).build();
//    }
//
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
