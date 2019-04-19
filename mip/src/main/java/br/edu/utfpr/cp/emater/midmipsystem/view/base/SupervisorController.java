package br.edu.utfpr.cp.emater.midmipsystem.view.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.MacroRegionService;
import br.edu.utfpr.cp.emater.midmipsystem.view.ICRUDController;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.FarmerService;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.SupervisorService;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class SupervisorController extends Supervisor implements ICRUDController<Supervisor> {

    private SupervisorService supervisorService;
    

    @Autowired
    public SupervisorController(SupervisorService aSupervisorService) {
        this.supervisorService = aSupervisorService;
    }

    @Override
    public List<Supervisor> readAll() {
        return supervisorService.readAll();
    }

//    @Override
//    public String create() {
//        var newFarmer = Farmer.builder().name(this.getName()).build();
//
//        try {
//            farmerService.create(newFarmer);
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", String.format("Produtor [%s] criado com sucesso!", this.getName())));
//            return "index.xhtml";
//
//        } catch (EntityAlreadyExistsException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um produtor com esse nome! Use um nome diferente."));
//            return "create.xhtml";
//
//        } catch (AnyPersistenceException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
//            return "index.xhtml";
//        }
//    }
//
//    @Override
//    public String prepareUpdate(Long anId) {
//
//        try {
//            Farmer existentFarmer = farmerService.readById(anId);
//            this.setId(existentFarmer.getId());
//            this.setName(existentFarmer.getName());
//
//            return "update.xhtml";
//
//        } catch (EntityNotFoundException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Produtor não pode ser alterado porque não foi encontrado na base de dados!"));
//            return "index.xhtml";
//        }
//    }
//
//    @Override
//    public String update() {
//        var updatedEntity = Farmer.builder().id(this.getId()).name(this.getName()).build();
//
//        try {
//            farmerService.update(updatedEntity);
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", String.format("Produtor alterado para [%s]!", updatedEntity.getName())));
//            return "index.xhtml";
//
//        } catch (EntityAlreadyExistsException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um produtor com esse nome! Use um nome diferente."));
//            return "update.xhtml";
//
//        } catch (EntityNotFoundException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Produtor não pode ser alterado porque não foi encontrado na base de dados!"));
//            return "update.xhtml";
//
//        } catch (AnyPersistenceException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
//            return "index.xhtml";
//        }
//
//    }
//
//    @Override
//    public String prepareDelete(Long anId) {
//
//        try {
//            var exixtentFarmer = farmerService.readById(anId);
//            this.setId(exixtentFarmer.getId());
//            this.setName(exixtentFarmer.getName());
//
//            return "delete.xhtml";
//
//        } catch (EntityNotFoundException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Produtor não pode ser excluído porque não foi encontrado na base de dados!"));
//            return "index.xhtml";
//        }
//    }
//
//    @Override
//    public String delete() {
//        
//        try {
//            farmerService.delete(this.getId());
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Produtor excluído!"));
//            return "index.xhtml";
//
//        } catch (EntityNotFoundException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Produtor não pode ser excluído porque não foi encontrado na base de dados!"));
//            return "index.xhtml";
//            
//        } catch (EntityInUseException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Produtor não pode ser excluído porque está sendo usado em uma unidade de referência!"));
//            return "index.xhtml";
//            
//        } catch (AnyPersistenceException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
//            return "index.xhtml";
//        }
//    }

    @Override
    public String create() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String prepareUpdate(Long anId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String prepareDelete(Long anId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
