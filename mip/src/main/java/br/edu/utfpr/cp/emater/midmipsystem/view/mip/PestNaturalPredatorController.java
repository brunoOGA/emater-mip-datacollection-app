package br.edu.utfpr.cp.emater.midmipsystem.view.mip;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestNaturalPredator;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.PestNaturalPredatorService;
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
public class PestNaturalPredatorController extends AbstractCRUDController<PestNaturalPredator> {

    private final PestNaturalPredatorService pestNaturalPredatorService;

    @Getter
    @Setter
    protected Long id;

    @Getter
    @Setter
    @Size(min = 5, max = 50, message = "O nome deve ter entre 5 e 50 caracteres")
    protected String usualName;

    @Override
    public List<PestNaturalPredator> readAll() {
        return pestNaturalPredatorService.readAll();
    }

    @Override
    protected void doCreate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        var newPestNaturalPredator = PestNaturalPredator.builder().usualName(this.getUsualName()).build();

        pestNaturalPredatorService.create(newPestNaturalPredator);
    }

    @Override
    protected void doPrepareUpdate(Long anId) throws EntityNotFoundException {
        var existentPestNaturalPredator = pestNaturalPredatorService.readById(anId);

        this.setId(existentPestNaturalPredator.getId());
        this.setUsualName(existentPestNaturalPredator.getUsualName());
    }

    @Override
    protected void doUpdate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        var updatedPestNaturalPredator = PestNaturalPredator.builder().id(this.getId()).usualName(this.getUsualName()).build();
        
        pestNaturalPredatorService.update(updatedPestNaturalPredator);
    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        pestNaturalPredatorService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Inimigo natural da praga";
    }
}
