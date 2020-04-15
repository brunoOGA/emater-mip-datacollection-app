package br.edu.utfpr.cp.emater.midmipsystem.view.mip;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestSize;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.PestService;
import br.edu.utfpr.cp.emater.midmipsystem.view.AbstractCRUDController;
import java.util.Arrays;
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
public class PestController extends AbstractCRUDController<Pest> {

    private final PestService pestService;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Size(min = 5, max = 50, message = "O nome deve ter entre 5 e 50 caracteres")
    private String usualName;

    @Getter
    @Setter
    private String scientificName;

    @Getter
    @Setter
    private PestSize pestSize;

    @Override
    public List<Pest> readAll() {
        return pestService.readAll();
    }

    public List<PestSize> readAllPestSizes() {
        return Arrays.asList(PestSize.values());
    }

    @Override
    protected void doCreate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        var newPest = Pest.builder().usualName(this.getUsualName()).scientificName(this.getScientificName()).pestSize(this.getPestSize()).build();

        pestService.create(newPest);
    }

    @Override
    public String prepareUpdate(Long anId) {

        try {
            Pest existentPest = pestService.readById(anId);
            this.setId(existentPest.getId());
            this.setUsualName(existentPest.getUsualName());
            this.setScientificName(existentPest.getScientificName());
            this.setPestSize(existentPest.getPestSize());

            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Inseto praga não pode ser alterado porque não foi encontrado na base de dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String update() {
        var updatedPest = Pest.builder().id(this.getId()).usualName(this.getUsualName()).scientificName(this.getScientificName()).pestSize(this.getPestSize()).build();

        try {
            pestService.update(updatedPest);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Inseto praga alterado"));
            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um inseto praga com esse nome/tamanho! Use um nome/tamanho diferente."));
            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Inseto praga não pode ser alterado porque não foi encontrado na base de dados!"));
            return "update.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }

    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        pestService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Inseto praga";
    }
}
