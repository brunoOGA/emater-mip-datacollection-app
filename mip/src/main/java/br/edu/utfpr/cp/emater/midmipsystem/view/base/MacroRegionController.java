package br.edu.utfpr.cp.emater.midmipsystem.view.base;

import br.edu.utfpr.cp.emater.midmipsystem.domain.base.macroRegion.MacroRegionService;
import br.edu.utfpr.cp.emater.midmipsystem.library.ICRUDController;
import br.edu.utfpr.cp.emater.midmipsystem.library.dtos.base.MacroRegionDTO;
import br.edu.utfpr.cp.emater.midmipsystem.library.exceptions.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.library.exceptions.EntityAlreadyExistsException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MacroRegionController extends MacroRegionDTO implements ICRUDController<MacroRegionDTO> {
    
    private MacroRegionService macroRegionService;

    @Autowired
    public MacroRegionController(MacroRegionService aMacroRegionService) {
        macroRegionService = aMacroRegionService;
    }

    @Override
    public List<MacroRegionDTO> readAll() {
        return macroRegionService.readAll();
    }

    @Override
    public String create() {
        MacroRegionDTO dto = MacroRegionDTO.builder().name(this.name).build();
        
        try {
            macroRegionService.create(dto);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", String.format("Macrorregião [%s] criada com sucesso!", dto.getName())));
            return "index.xhtml";
            
        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe uma macrorregião com esse nome!"));
            return "create.xhtml";
            
        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String delete(int id) {
       
        return null;
    }
    
}
