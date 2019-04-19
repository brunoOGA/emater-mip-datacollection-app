package br.edu.utfpr.cp.emater.midmipsystem.view.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.MacroRegionService;
import br.edu.utfpr.cp.emater.midmipsystem.view.ICRUDController;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.CityService;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.FarmerService;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.FieldService;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.SupervisorService;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class FieldController extends Field implements ICRUDController<Field> {

    private FieldService fieldService;

    @Getter
    @Setter
    private String selectedSupervisors;

    @Autowired
    public FieldController(FieldService aFieldService) {
        this.fieldService = aFieldService;
    }

    @Override
    public List<Field> readAll() {
        return fieldService.readAll();
    }

    public List<City> readAllCities() {
        return fieldService.readAllCities();
    }

    public List<Farmer> readAllFarmers() {
        return fieldService.readAllFarmers();
    }

    public List<Supervisor> readAllSupervisors() {
        return fieldService.readAllSupervisors();
    }

    private Set<Long> convertStringSupervisorsIdToSetId() {

        String stringIDs[] = this.getSelectedSupervisors().split(",");

        var result = new HashSet<Long>();

        for (String currentStringID : stringIDs) {
            result.add(new Long(currentStringID));
        }

        return result;
    }

    private Set<Supervisor> retrieveSupervisors(Set<Long> ids) throws EntityNotFoundException {

        var result = new HashSet<Supervisor>();

        var allSupervisorEntities = fieldService.readAllSupervisors();

        for (Long currentId : ids) {
            result.add(
                    allSupervisorEntities
                            .stream()
                            .filter(
                                    currentSupervisor -> currentSupervisor
                                            .getId().equals(currentId))
                            .findAny()
                            .orElseThrow(EntityNotFoundException::new));
        }

        return result;
    }

    @Override
    public String create() {

        try {
            var newField = Field.builder()
                    .name(this.getName())
                    .location(this.getLocation())
                    .city(this.getCity())
                    .farmer(this.getFarmer())
                    .supervisors(retrieveSupervisors(this.convertStringSupervisorsIdToSetId()))
                    .build();

            fieldService.create(newField);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", String.format("Unidade de referência [%s] criado com sucesso!", this.getName())));
            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe uma unidade de referência com esse nome, nessa cidade para esse produtor!"));
            return "create.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Unidade de referência não pode ser criada porque não foram encontradas as referências para cidade, produtor ou responsável técnico na base de dados!"));
            return "index.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }
    }

    private String convertEntitySupervisorsToStringId(Set<Supervisor> supervisors) {

        var idList = supervisors.stream().map(Supervisor::getId).collect(Collectors.toList());
        var count = idList.size() - 1;

        var idListStringBuilder = new StringBuilder();

        for (Long currentId : idList) {
            idListStringBuilder.append(String.valueOf(currentId));

            if (count > 0) {
                idListStringBuilder.append(",");
                count--;
            }
        }

        return idListStringBuilder.toString();
    }

    @Override
    public String prepareUpdate(Long anId) {

        try {
            Field existentField = fieldService.readById(anId);
            this.setId(existentField.getId());
            this.setName(existentField.getName());
            this.setLocation(existentField.getLocation());
            this.setCity(existentField.getCity());
            this.setFarmer(existentField.getFarmer());
            this.setSelectedSupervisors(convertEntitySupervisorsToStringId(existentField.getSupervisors()));

            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Produtor não pode ser alterado porque não foi encontrado na base de dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String update() {

        try {
            var updatedField = Field.builder()
                    .id(this.getId())
                    .name(this.getName())
                    .location(this.getLocation())
                    .city(this.getCity())
                    .farmer(this.getFarmer())
                    .supervisors(retrieveSupervisors(this.convertStringSupervisorsIdToSetId()))
                    .build();

            fieldService.update(updatedField);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Unidade de referência alterada"));
            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe uma unidade de referência com esse nome, nessa cidade para esse produtor!"));
            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Unidade de referência não pode ser alterada porque não foi encontrada na base de dados!"));
            return "update.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }

    }

    @Override
    public String prepareDelete(Long anId) {

        try {
            var existentField = fieldService.readById(anId);
            this.setId(existentField.getId());
            this.setName(existentField.getName());

            return "delete.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Unidade de referência não pode ser excluída porque não foi encontrada na base de dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String delete() {
        
        try {
            fieldService.delete(this.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Unidade de referência excluída!"));
            return "index.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Unidade de referência não pode ser excluída porque não foi encontrada na base de dados!"));
            return "index.xhtml";
            
        } catch (EntityInUseException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Unidade de referência não pode ser excluída porque está sendo usado em uma pesquisa!"));
            return "index.xhtml";
            
        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }
    }

}
