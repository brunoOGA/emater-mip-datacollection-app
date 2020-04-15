package br.edu.utfpr.cp.emater.midmipsystem.view;

import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.security.access.AccessDeniedException;

public abstract class AbstractCRUDController<T> implements ICRUDController<T>, Serializable {

    @Override
    public String create() {

        try {
            this.doCreate();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", String.format("%s criado(a) com sucesso!", this.getItemName())));
            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", String.format("Já existe uma %s com esse nome! Use um nome diferente.", this.getItemName().toLowerCase())));
            return "create.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", String.format("A %s não pode ser criado(a) porque os elemtnos que o(a) compõem não foram encontradas na base de dados!", this.getItemName().toLowerCase())));
            return "create.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }
    }

    protected abstract void doCreate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException;

    @Override
    public String prepareUpdate(Long anId) {
        try {
            this.doPrepareUpdate(anId);

            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", String.format("%s não pode ser alterado(a) porque não foi encontrado(a) na base de dados!", this.getItemName())));
            return "index.xhtml";
        }
    }

    protected abstract void doPrepareUpdate(Long anId) throws EntityNotFoundException;

    @Override
    public String delete(Long anId) {
        try {

            this.doDelete(anId);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", String.format("%s excluído(a)!", this.getItemName())));
            return "index.xhtml";

        } catch (AccessDeniedException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", String.format("%s não pode ser excluído(a) porque o usuário não está autorizado!", this.getItemName())));
            return "index.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", String.format("%s não pode ser excluído(a) porque não foi encontrado(a) na base de dados!", this.getItemName())));
            return "index.xhtml";

        } catch (EntityInUseException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", String.format("%s não pode ser excluído(a) porque está sendo usado(a) em uma pesquisa!", this.getItemName())));
            return "index.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }
    }

    protected abstract void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException;

    protected abstract String getItemName();

}
