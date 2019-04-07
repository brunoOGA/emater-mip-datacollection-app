package br.edu.utfpr.cp.emater.midmipsystem.domain.base.macroRegion;

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
    
}
