package br.edu.utfpr.cp.emater.midmipsystem.view.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.SupervisorService;
import br.edu.utfpr.cp.emater.midmipsystem.view.AbstractCRUDController;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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
public class SupervisorController extends AbstractCRUDController<Supervisor> {

    private final SupervisorService supervisorService;

    @Getter
    @Setter
    @Email(message = "Deve ser informado um e-mail válido")
    @NotNull(message = "Deve ser informado um e-mail válido")

    private String email;

    @Getter
    @Setter
    private Region region;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    private String name;

    @Getter
    @Setter
    private Long selectedRegionId;

    @Override
    public List<Supervisor> readAll() {
        return supervisorService.readAll();
    }

    public List<Region> readAllRegions() {
        return supervisorService.readAllRegions();
    }

    @Override
    protected void doCreate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        var newSupervisor = Supervisor.builder().name(this.getName()).email(this.getEmail()).region(this.supervisorService.readRegionById(this.getSelectedRegionId())).build();

        supervisorService.create(newSupervisor);
    }

    @Override
    protected void doPrepareUpdate(Long anId) throws EntityNotFoundException {
        Supervisor existentSupervisor = supervisorService.readById(anId);

        this.setId(existentSupervisor.getId());
        this.setName(existentSupervisor.getName());
        this.setEmail(existentSupervisor.getEmail());
        this.setSelectedRegionId(existentSupervisor.getRegionId());
    }

    @Override
    protected void doUpdate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        var updatedSupervisor = Supervisor.builder().id(this.getId()).name(this.getName()).email(this.getEmail()).region(this.supervisorService.readRegionById(this.getSelectedRegionId())).build();
        supervisorService.update(updatedSupervisor);
    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        supervisorService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Responsável técnico";
    }

}
