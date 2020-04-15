package br.edu.utfpr.cp.emater.midmipsystem.view.survey;

import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.HarvestService;
import br.edu.utfpr.cp.emater.midmipsystem.view.AbstractCRUDController;
import java.util.Date;
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
public class HarvestController extends AbstractCRUDController<Harvest> {

    private final HarvestService harvestService;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Size(min = 5, max = 50, message = "A identificação da safra deve ter entre 5 e 50 caracteres")
    private String name;

    @Getter
    @Setter
    private Date begin;

    @Getter
    @Setter
    private Date end;

    @Override
    public List<Harvest> readAll() {
        return harvestService.readAll();
    }

    @Override
    protected void doCreate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        var newHarvest = Harvest.builder().name(this.getName()).begin(this.getBegin()).end(this.getEnd()).build();

        harvestService.create(newHarvest);
    }

    @Override
    public String prepareUpdate(Long anId) {

        try {
            var existentHarvest = harvestService.readById(anId);
            this.setId(existentHarvest.getId());
            this.setName(existentHarvest.getName());
            this.setBegin(existentHarvest.getBegin());
            this.setEnd(existentHarvest.getEnd());

            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Macrorregião não pode ser alterada porque não foi encontrada na base de dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String update() {

        var updatedEntity = Harvest.builder()
                .id(this.getId())
                .name(this.getName())
                .begin(this.getBegin())
                .end(this.getEnd())
                .build();

        try {
            harvestService.update(updatedEntity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Safra alterada!"));
            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe uma safra começando e terminando nessas datas! Use um datas diferentes."));
            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Safra não pode ser alterada porque não foi encontrada na base de dados!"));
            return "update.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }

    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        harvestService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Safra";
    }

}
