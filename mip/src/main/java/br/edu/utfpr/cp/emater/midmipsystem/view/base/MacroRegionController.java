package br.edu.utfpr.cp.emater.midmipsystem.view.base;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component (value = "macroRegionController")
@Data
public class MacroRegionController {
    
    private List<MacroRegionDTO> macroRegions;
    
    public MacroRegionController () {
        this.macroRegions = Stream.of(new MacroRegionDTO(new Long(10), "John"), new MacroRegionDTO(new Long(20), "Michel"), new MacroRegionDTO(new Long(30), "Anna")).collect(Collectors.toList());
    }
    
    public void delete (Long id) {
        macroRegions.removeIf(c -> c.getId() == id);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PrimeFaces Rocks."));
    }
    
}
