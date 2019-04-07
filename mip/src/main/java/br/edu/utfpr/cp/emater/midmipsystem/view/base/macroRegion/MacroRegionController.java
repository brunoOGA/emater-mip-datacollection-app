package br.edu.utfpr.cp.emater.midmipsystem.view.base.macroRegion;

import br.edu.utfpr.cp.emater.midmipsystem.domain.base.macroRegion.MacroRegionService;
import br.edu.utfpr.cp.emater.midmipsystem.library.controllers.AbstractCRUDController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping (value = "/base/macro-region")
public class MacroRegionController extends AbstractCRUDController<MacroRegionDTO> {
    
    private final MacroRegionService macroRegionService;
    
    private final Environment environment;

    @Autowired
    public MacroRegionController(MacroRegionService aMacroRegionService, Environment anEnvironment) {
        this.macroRegionService = aMacroRegionService;
        this.environment = anEnvironment;
    }

    @Override
    public String create(MacroRegionDTO entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @RequestMapping (value = "/new", method = RequestMethod.GET)
    public String goNew () {
        return this.environment.getProperty("view.route.template.new.base.macro-region");
    } 

    @Override
    @RequestMapping (value = "/readAll", method = RequestMethod.GET)
    public String readAll(Model data) {
        data.addAttribute("macroregions", macroRegionService.readAll());
        data.addAttribute("success", this.operationSuccessMessage);

        this.resetOperationSuccessMessage();
        
        return this.environment.getProperty("view.route.template.index.base.macro-region");
    }

    @Override
    public String update(MacroRegionDTO entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
