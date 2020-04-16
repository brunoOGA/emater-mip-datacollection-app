package br.edu.utfpr.cp.emater.midmipsystem.view.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.MacroRegionService;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.view.AbstractCRUDController;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@RequiredArgsConstructor
public class MacroRegionController extends AbstractCRUDController<MacroRegion> {

    private final MacroRegionService macroRegionService;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Size(min = 3, max = 50, message = "O nome da macrorregião deve ter entre 3 e 50 caracteres")
    private String name;

    @Override
    public List<MacroRegion> readAll() {
        return macroRegionService.readAll();
    }

    @Override
    protected void doCreate() throws EntityAlreadyExistsException, AnyPersistenceException {
        MacroRegion newMacroRegion = MacroRegion.builder().name(this.getName()).build();
        macroRegionService.create(newMacroRegion);
    }

    @Override
    protected void doPrepareUpdate(Long anId) throws EntityNotFoundException {
        MacroRegion existentMacroRegion = macroRegionService.readById(anId);
        this.setId(existentMacroRegion.getId());
        this.setName(existentMacroRegion.getName());
    }

    @Override
    protected void doUpdate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        MacroRegion updatedEntity = MacroRegion.builder().id(this.getId()).name(this.getName()).build();
        macroRegionService.update(updatedEntity);
    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        macroRegionService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Macrorregião";
    }
}
