package br.edu.utfpr.cp.emater.midmipsystem.view.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.view.ICRUDController;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.FarmerService;
import br.edu.utfpr.cp.emater.midmipsystem.view.AbstractCRUDController;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@RequiredArgsConstructor
public class FarmerController extends AbstractCRUDController<Farmer> {

    private final FarmerService farmerService;
    
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;

    @Override
    public List<Farmer> readAll() {
        return farmerService.readAll();
    }

    @Override
    public String create() {
        var newFarmer = Farmer.builder().name(this.getName()).build();

        try {
            farmerService.create(newFarmer);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", String.format("Produtor [%s] criado com sucesso!", this.getName())));
            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um produtor com esse nome! Use um nome diferente."));
            return "create.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String prepareUpdate(Long anId) {

        try {
            Farmer existentFarmer = farmerService.readById(anId);
            this.setId(existentFarmer.getId());
            this.setName(existentFarmer.getName());

            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Produtor não pode ser alterado porque não foi encontrado na base de dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String update() {
        var updatedEntity = Farmer.builder().id(this.getId()).name(this.getName()).build();

        try {
            farmerService.update(updatedEntity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", String.format("Produtor alterado para [%s]!", updatedEntity.getName())));
            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um produtor com esse nome! Use um nome diferente."));
            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Produtor não pode ser alterado porque não foi encontrado na base de dados!"));
            return "update.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }

    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        farmerService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Produtor";
    }

}
