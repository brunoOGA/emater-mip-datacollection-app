package br.edu.utfpr.cp.emater.midmipsystem.view.survey;

import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Cultivar;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.CultivarService;
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
public class CultivarController extends AbstractCRUDController<Cultivar> {

    private final CultivarService cultivarService;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Size(min = 3, max = 50, message = "A identificação da cultivar deve ter entre 3 e 50 caracteres")
    private String name;

    @Override
    public List<Cultivar> readAll() {
        return cultivarService.readAll();
    }

    @Override
    protected void doCreate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        var newCultivar = Cultivar.builder().name(this.getName()).build();

        cultivarService.create(newCultivar);
    }

    @Override
    protected void doPrepareUpdate(Long anId) throws EntityNotFoundException {
        var existentCultivar = cultivarService.readById(anId);
        
        this.setId(existentCultivar.getId());
        this.setName(existentCultivar.getName());
    }

    @Override
    public String update() {

        var updatedEntity = Cultivar.builder()
                .id(this.getId())
                .name(this.getName())
                .build();

        try {
            cultivarService.update(updatedEntity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cultivar alterada!"));
            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe uma cultivar com esse nome."));
            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Cultivar não pode ser alterada porque não foi encontrada na base de dados!"));
            return "update.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }

    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        cultivarService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Cultivar";
    }

}
