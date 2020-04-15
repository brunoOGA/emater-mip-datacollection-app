package br.edu.utfpr.cp.emater.midmipsystem.view.mip;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestDisease;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.PestDiseaseService;
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
public class PestDiseaseController extends AbstractCRUDController<PestDisease> {

    private final PestDiseaseService pestDiseaseService;

    @Getter
    @Setter
    protected Long id;

    @Getter
    @Setter
    @Size(min = 5, max = 50, message = "O nome deve ter entre 5 e 50 caracteres")
    protected String usualName;

    @Override
    public List<PestDisease> readAll() {
        return pestDiseaseService.readAll();
    }

    @Override
    protected void doCreate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        var newPestDisease = PestDisease.builder().usualName(this.getUsualName()).build();

        pestDiseaseService.create(newPestDisease);
    }

    @Override
    protected void doPrepareUpdate(Long anId) throws EntityNotFoundException {
        var existentPestDisease = pestDiseaseService.readById(anId);

        this.setId(existentPestDisease.getId());
        this.setUsualName(existentPestDisease.getUsualName());
    }

    @Override
    public String update() {
        var updatedPestDisease = PestDisease.builder().id(this.getId()).usualName(this.getUsualName()).build();

        try {
            pestDiseaseService.update(updatedPestDisease);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Doença da praga alterada"));
            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe uma doença da praga com esse nome! Use um nome diferente."));
            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Doença da praga não pode ser alterada porque não foi encontrada na base de dados!"));
            return "update.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }

    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        pestDiseaseService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Doença da praga";
    }
}
