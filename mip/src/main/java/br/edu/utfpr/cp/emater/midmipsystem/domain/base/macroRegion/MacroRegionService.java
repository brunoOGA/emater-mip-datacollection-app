package br.edu.utfpr.cp.emater.midmipsystem.domain.base.macroRegion;

import br.edu.utfpr.cp.emater.midmipsystem.library.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.library.EntityAlreadyExistsException;
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
    
    public List<MacroRegion> readAll() {
        return List.copyOf(macroRegionRepository.findAll());
    }
    
    public void create (MacroRegion aMacroRegion) throws EntityAlreadyExistsException, AnyPersistenceException {
        if (this.readAll().stream().anyMatch(mr -> mr.equals(aMacroRegion)))
                throw new EntityAlreadyExistsException();
        
        try {
            macroRegionRepository.save(aMacroRegion);
            
        } catch (Exception e) {
            throw new AnyPersistenceException();
        }  
    }
    
}
