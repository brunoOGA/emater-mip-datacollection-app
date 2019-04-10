package br.edu.utfpr.cp.emater.midmipsystem.view.base;

import br.edu.utfpr.cp.emater.midmipsystem.library.ICRUDController;
import br.edu.utfpr.cp.emater.midmipsystem.library.dtos.base.MacroRegionDTO;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;
import org.springframework.stereotype.Component;

@Component
public class MacroRegionController extends MacroRegionDTO implements ICRUDController<MacroRegionDTO> {

    private List<MacroRegionDTO> macroRegions;

    public MacroRegionController() {
        this.macroRegions = Stream.of(new MacroRegionDTO(new Long("1"), "Macro Noroeste"), new MacroRegionDTO(new Long("2"), "Macro Noroeste"), new MacroRegionDTO(new Long("3"), "Macro Noroeste"), new MacroRegionDTO(new Long("4"), "Macro Noroeste")).collect(Collectors.toList());
    }

    @Override
    public List<MacroRegionDTO> readAll() {
        return macroRegions;
    }

//    public void delete (Long id) {
////        macroRegions.removeIf(c -> c.getId() == id);
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PrimeFaces Rocks."));
//    }
    @Override
    public String create() {
        MacroRegionDTO dto = MacroRegionDTO.builder().id(new Long("10")).name(this.name).build();

        if (this.readAll().stream().anyMatch(mr -> mr.getName().equalsIgnoreCase(dto.getName()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Já existe uma macrorregião com esse nome!"));
            return "create.xhtml";
            
        } else {
            this.macroRegions.add(dto);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Macrorregião criada com sucesso!"));
            return "index.xhtml";
        }
        
    }

}
