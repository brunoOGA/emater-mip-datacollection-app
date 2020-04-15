package br.edu.utfpr.cp.emater.midmipsystem.view.mid;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.BladeReadingResponsibleEntity;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.mid.BladeReadingResponsibleEntityService;
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
public class BladeReadingResponsibleEntityController extends AbstractCRUDController<BladeReadingResponsibleEntity> {

    private final BladeReadingResponsibleEntityService bladeReadingEntityService;
    
    @Getter @Setter
    private Long id;

    @Getter @Setter
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    private String name;

    @Getter
    @Setter
    private Long selectedCityId;

    @Override
    public List<BladeReadingResponsibleEntity> readAll() {
        return bladeReadingEntityService.readAll();
    }

    public List<City> readAllCities() {
        return bladeReadingEntityService.readAllCities();
    }

    @Override
    protected void doCreate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
            var newEntity = BladeReadingResponsibleEntity.builder()
                    .name(this.getName())
                    .city(this.bladeReadingEntityService.readCityById(this.getSelectedCityId()))
                    .build();

            bladeReadingEntityService.create(newEntity);
    }
    
    @Override
    public String prepareUpdate(Long anId) {

        try {
            var existentEntity = bladeReadingEntityService.readById(anId);
            this.setId(existentEntity.getId());
            this.setName(existentEntity.getName());
            this.setSelectedCityId(existentEntity.getCityId());

            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Entidade não pode ser alterada porque não foi encontrada na base de dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String update() {

        try {
            var updatedEntity = BladeReadingResponsibleEntity.builder()
                    .id(this.getId())
                    .name(this.getName())
                    .city(bladeReadingEntityService.readCityById(this.getSelectedCityId()))
                    .build();

            bladeReadingEntityService.update(updatedEntity);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Entidade alterada!"));

            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe uma entidade com esse nome! Use um nome diferente."));
            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Entidade não pode ser alterada porque não foi encontrada na base de dados!"));
            return "update.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }

    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        bladeReadingEntityService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Entidade";
    }

}
