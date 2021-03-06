package br.edu.utfpr.cp.emater.midmipsystem.view.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Target;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.UseClass;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.pulverisation.TargetService;
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
public class TargetController extends AbstractCRUDController<Target> {

    private final TargetService targetService;

    @Getter
    @Setter
    protected Long id;

    @Getter
    @Setter
    @Size(min = 3, max = 80, message = "A descrição deve ter entre 3 e 80 caracteres")
    protected String description;

    @Getter
    @Setter
    private UseClass useClass;

    @Override
    public List<Target> readAll() {
        return targetService.readAll();
    }

    public UseClass[] readAllUseClasses() {
        return UseClass.values();
    }

    @Override
    protected void doCreate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        var newTarget = Target.builder().description(this.getDescription()).useClass(this.getUseClass()).build();

        targetService.create(newTarget);
    }

    @Override
    protected void doPrepareUpdate(Long anId) throws EntityNotFoundException {
        var existentTarget = targetService.readById(anId);

        this.setId(existentTarget.getId());
        this.setDescription(existentTarget.getDescription());
        this.setUseClass(existentTarget.getUseClass());
    }

    @Override
    protected void doUpdate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        var updatedTarget = Target.builder().id(this.getId()).description(this.getDescription()).useClass(this.getUseClass()).build();

        targetService.update(updatedTarget);
    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        targetService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Alvo/Função";
    }
}
