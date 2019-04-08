package br.edu.utfpr.cp.emater.midmipsystem.view.base;

import br.edu.utfpr.cp.emater.midmipsystem.domain.base.macroRegion.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.domain.base.macroRegion.MacroRegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.domain.base.macroRegion.MacroRegionService;
import br.edu.utfpr.cp.emater.midmipsystem.library.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.library.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.library.ICRUDController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component (value = "macroRegionController")
public class MacroRegionController implements ICRUDController<MacroRegion> {
        
    private final MacroRegionService macroRegionService;
            
    @Getter
    @Setter
    private String name;

    @Autowired
    public MacroRegionController(MacroRegionService aMacroRegionService) {
        this.macroRegionService = aMacroRegionService;
    }
    
    @Override
    public List<MacroRegion> readAll() {
        return macroRegionService.readAll();
    }
    
    public void delete (Long id) {
//        macroRegions.removeIf(c -> c.getId() == id);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PrimeFaces Rocks."));
    }

    @Override
    public void create () {
        
        try {
            MacroRegion mr = new MacroRegion();
            mr.setName(this.getName());
            
            macroRegionService.create(mr);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Macrorregião criada com sucesso!"));
            
        } catch (EntityAlreadyExistsException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Já existe uma macrorregião com esse nome!"));
            Logger.getLogger(MacroRegionController.class.getName()).log(Level.SEVERE, null, ex);

        } catch (AnyPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Info", "Erro na gravação dos dados!"));
            Logger.getLogger(MacroRegionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
