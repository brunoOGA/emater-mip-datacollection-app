package br.edu.utfpr.cp.emater.midmipsystem.view.base;

import br.edu.utfpr.cp.emater.midmipsystem.service.base.MacroRegionService;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.RegionService;
import br.edu.utfpr.cp.emater.midmipsystem.view.ICRUDController;
import br.edu.utfpr.cp.emater.midmipsystem.library.dtos.base.CityDTO;
import br.edu.utfpr.cp.emater.midmipsystem.library.dtos.base.MacroRegionDTO;
import br.edu.utfpr.cp.emater.midmipsystem.library.dtos.base.RegionDTO;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegionController extends RegionDTO implements ICRUDController<RegionDTO> {
    
    private final RegionService regionService;
    private final MacroRegionService macroRegionService;
    
    @Autowired
    public RegionController (RegionService aRegionService, MacroRegionService aMacroRegionService) {
        this.regionService = aRegionService;
        this.macroRegionService = aMacroRegionService;
    }

    @Override
    public List<RegionDTO> readAll() {
        return regionService.readAll();
    }
    
    public List<MacroRegionDTO> readAllMacroRegions() {
//        return macroRegionService.readAll();
return null;
    }

    @Override
    public String create() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String prepareUpdate(Long anId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String prepareDelete(Long anId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
