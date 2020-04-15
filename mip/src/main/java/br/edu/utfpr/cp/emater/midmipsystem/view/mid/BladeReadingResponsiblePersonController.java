package br.edu.utfpr.cp.emater.midmipsystem.view.mid;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.BladeReadingResponsibleEntity;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.BladeReadingResponsiblePerson;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.mid.BladeReadingResponsiblePersonService;
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
public class BladeReadingResponsiblePersonController extends AbstractCRUDController<BladeReadingResponsiblePerson> {

    private final BladeReadingResponsiblePersonService bladeReadingPersonService;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    private String name;
    
    @Getter
    @Setter
    private Long selectedEntityId;

    @Override
    public List<BladeReadingResponsiblePerson> readAll() {
        return bladeReadingPersonService.readAll();
    }

    public List<BladeReadingResponsibleEntity> readAllEntities() {
        return bladeReadingPersonService.readAllEntities();
    }

    @Override
    protected void doCreate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
            var newPerson = BladeReadingResponsiblePerson.builder()
                    .name(this.getName())
                    .entity(this.bladeReadingPersonService.readEntityById(this.getSelectedEntityId()))
                    .build();

            bladeReadingPersonService.create(newPerson);
    }

    @Override
    public String prepareUpdate(Long anId) {

        try {
            var existentPerson = bladeReadingPersonService.readById(anId);
            this.setId(existentPerson.getId());
            this.setName(existentPerson.getName());
            this.setSelectedEntityId(existentPerson.getEntityId());

            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Profissional não pode ser alterado porque não foi encontrado na base de dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String update() {

        try {
            var updatedPerson = BladeReadingResponsiblePerson.builder()
                    .id(this.getId())
                    .name(this.getName())
                    .entity(this.bladeReadingPersonService.readEntityById(this.getSelectedEntityId()))
                    .build();

            bladeReadingPersonService.update(updatedPerson);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Profissional alterado!"));

            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um profissional com esse nome! Use um nome diferente."));
            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Profissional não pode ser alterado porque não foi encontrado na base de dados!"));
            return "update.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }

    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        bladeReadingPersonService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Profissional";
    }

}
