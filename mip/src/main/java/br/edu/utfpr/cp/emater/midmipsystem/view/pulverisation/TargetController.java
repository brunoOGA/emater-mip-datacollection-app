package br.edu.utfpr.cp.emater.midmipsystem.view.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.view.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Target;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.TargetCategory;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.RegionService;
import br.edu.utfpr.cp.emater.midmipsystem.service.pulverisation.TargetService;
import br.edu.utfpr.cp.emater.midmipsystem.view.ICRUDController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

// Note that there are issues to resolve when updating a Region
@Component
@RequestScope
public class TargetController extends Target implements ICRUDController<Target> {

    private final TargetService targetService;

    @Getter
    @Setter
    private List<City> selectedCities;

    @Getter
    @Setter
    private Long selectedMacroRegion;

    @Autowired
    public TargetController(TargetService aTargetService) {
        this.targetService = aTargetService;
    }

    @Override
    public List<Target> readAll() {
        return targetService.readAll();
    }
    
    public TargetCategory[] readAllTargetCategories() {
        return TargetCategory.values();
    }

    @Override
    public String create() {

        try {
            var newTarget = Target.builder().description(this.getDescription()).category(this.getCategory()).build();
            
            targetService.create(newTarget);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", String.format("Alvo/Função [%s] criado com sucesso!", newTarget.getDescription())));
            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um alvo/função com esse nome para essa categoria! Use um nome diferente."));
            return "create.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Alvo/Função não pode ser criado porque a categoria não foi encontrada na base de dados!"));
            return "create.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String prepareUpdate(Long anId) {

        try {
            var existentTarget = targetService.readById(anId);
            
            this.setId(existentTarget.getId());
            this.setDescription(existentTarget.getDescription());
            this.setCategory(existentTarget.getCategory());

            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Alvo/Função não pode ser alterado porque não foi encontrado na base de dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String update() {

        try {
            var updatedTarget = Target.builder().id(this.getId()).description(this.getDescription()).category(this.getCategory()).build();

            targetService.update(updatedTarget);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Alvo/Função alterada!"));

            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um alvo/função com esse nome! Use um nome diferente."));
            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Alvo/Função não pode ser alterada porque não foi encontrada na base de dados!"));
            return "update.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }

    }

    public String delete(Long anId) {

        try {
            targetService.delete(anId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Alvo/Função excluído!"));

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Alvo/Função não pode ser excluído porque não foi encontrado na base de dados!"));

        } catch (EntityInUseException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Alvo/Função não pode ser excluído porque está sendo usado no sistema!"));

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
        }

        return "index.xhtml";
    }

}
