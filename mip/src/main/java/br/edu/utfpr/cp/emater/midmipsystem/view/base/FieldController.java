package br.edu.utfpr.cp.emater.midmipsystem.view.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.view.ICRUDController;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.SupervisorNotAllowedInCity;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.FieldService;
import br.edu.utfpr.cp.emater.midmipsystem.view.AbstractCRUDController;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

// Note that there are issues to resolve regarding the match of supervisors and cities
@Component
@RequestScope
@RequiredArgsConstructor
public class FieldController extends AbstractCRUDController<Field> {

    private final FieldService fieldService;
    
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;
    
    @Getter @Setter
    private String location;

    @Getter @Setter
    private City city;

    @Getter @Setter
    private Farmer farmer;

    @Getter @Setter
    private Set<Supervisor> supervisors;

    @Getter
    @Setter
    private List<Long> selectedSupervisorIds;

    @Getter
    @Setter
    private Long selectedCityId;

    @Getter
    @Setter
    private Long selectedFarmerId;

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

    @Override
    public String create() {

        try {
            var newField = Field.builder()
                    .name(this.getName())
                    .location(this.getLocation())
                    .city(this.fieldService.readCityById(this.getSelectedCityId()))
                    .farmer(this.fieldService.readFarmerById(this.getSelectedFarmerId()))
                    .supervisors(fieldService.readSupervisorsByIds(this.getSelectedSupervisorIds()))
                    .build();

            fieldService.create(newField);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Unidade de referência criada com sucesso!"));
            return "index.xhtml";

        } catch (SupervisorNotAllowedInCity ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Um ou mais responsáveis técnicos selecionados não atendem a cidade selecionada para essa UR!"));
            return "create.xhtml";

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

    @Override
    public String prepareUpdate(Long anId) {

        try {
            Field existentField = fieldService.readById(anId);
            this.setId(existentField.getId());
            this.setName(existentField.getName());
            this.setLocation(existentField.getLocation());
            this.setSelectedCityId(existentField.getCityId());
            this.setSelectedFarmerId(existentField.getFarmerId());
            this.setSelectedSupervisorIds(existentField.getSupervisors().stream().map(Supervisor::getId).collect(Collectors.toList()));

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
                    .city(this.fieldService.readCityById(this.getSelectedCityId()))
                    .farmer(this.fieldService.readFarmerById(this.getSelectedFarmerId()))
                    .supervisors(fieldService.readSupervisorsByIds(this.getSelectedSupervisorIds()))
                    .build();

            fieldService.update(updatedField);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Unidade de referência alterada"));
            return "index.xhtml";

        } catch (SupervisorNotAllowedInCity ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Um ou mais responsáveis técnicos selecionados não atendem a cidade selecionada para essa UR!"));
            return "update.xhtml";

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
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        fieldService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Unidade de referência";
    }

}
