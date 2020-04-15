package br.edu.utfpr.cp.emater.midmipsystem.view.security;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.security.Authority;
import br.edu.utfpr.cp.emater.midmipsystem.entity.security.MIPUser;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.security.MIPUserService;
import br.edu.utfpr.cp.emater.midmipsystem.view.AbstractCRUDController;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component(value = "mipUserController")
@SessionScope
@RequiredArgsConstructor
public class MIPUserController extends AbstractCRUDController<MIPUser> {

    private final MIPUserService userService;

    @Getter
    @Setter
    private Long id;

    @Size(min = 5, message = "O nome completo deve ser maior que 5 caracteres")
    @Getter
    @Setter
    private String fullName;

    @Email(message = "Deve ser informado um e-mail válido")
    @Getter
    @Setter
    private String email;

    @Size(min = 3, max = 30, message = "O nome de usuário deve ter entre 3 e 20 caracteres")
    @Getter
    @Setter
    private String username;

    @Size(min = 3, message = "A senha deve ser maior que 3 caracteres")
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private Region region;

    @Getter
    @Setter
    private City city;

    @Getter
    @Setter
    private boolean accountNonExpired;

    @Getter
    @Setter
    private boolean accountNonLocked;

    @Getter
    @Setter
    private boolean credentialsNonExpired;

    @Getter
    @Setter
    private boolean enabled = true;

    @Getter
    @Setter
    private Long selectedRegionId;

    @Getter
    @Setter
    private Long selectedCityId;

    @Getter
    @Setter
    private List<City> allCitiesAvailableToSelectedRegion;

    @Getter
    @Setter
    private String passwordConfirmation;

    @Getter
    @Setter
    private List<Long> selectedUserTypeIds;

    private List<Authority> convertAuthorityIDsToObjectList() throws EntityNotFoundException {

        var result = new ArrayList<Authority>();

        for (Long id : selectedUserTypeIds) {
            result.add(userService.readAuthorityById(id).orElseThrow(EntityNotFoundException::new));
        }

        return result;
    }

    private List<Long> convertObjectListToAuthorityIDs(List<Authority> authorities) {
        var result = new ArrayList<Long>();

        for (Authority currentAuthority : authorities) {
            result.add(currentAuthority.getId());
        }

        return result;
    }

    public List<Authority> readAllUserTypes() {
        return userService.readAllUserTypes();
    }

    public List<Region> readAllRegions() {
        return userService.readAllRegions();
    }

    public void onSelectRegionChange() {
        try {
            this.setAllCitiesAvailableToSelectedRegion(userService.readAllCitiesInRegion(this.getSelectedRegionId()));

        } catch (EntityNotFoundException ex) {
            this.setAllCitiesAvailableToSelectedRegion(null);
        }
    }

    @Override
    public List<MIPUser> readAll() {
        return userService.readAll();
    }

    @Override
    protected void doCreate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {

        if (!this.getPassword().equals(this.getPasswordConfirmation())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Senha não foi confirmada corretamente!"));
//            return "create.xhtml";
        }

        var newUser = MIPUser.builder()
                .accountNonExpired(!this.isAccountNonExpired())
                .accountNonLocked(!this.isAccountNonLocked())
                .city(userService.readCityById(this.getSelectedCityId()))
                .credentialsNonExpired(!this.isCredentialsNonExpired())
                .email(this.getEmail())
                .enabled(this.isEnabled())
                .fullName(this.getFullName())
                .password(this.getPassword())
                .region(userService.readRegionById(this.getSelectedRegionId()))
                .username(this.getUsername())
                .authorities(this.convertAuthorityIDsToObjectList())
                .build();

        userService.create(newUser);
    }

    @Override
    public String prepareUpdate(Long anId) {

        try {

            var currentUser = userService.readById(anId);

            this.setAccountNonExpired(!currentUser.isAccountNonExpired());
            this.setAccountNonLocked(!currentUser.isAccountNonLocked());
            this.setCredentialsNonExpired(!currentUser.isCredentialsNonExpired());
            this.setEnabled(currentUser.isEnabled());

            this.setEmail(currentUser.getEmail());
            this.setFullName(currentUser.getFullName());
            this.setUsername(currentUser.getUsername());
            this.setId(currentUser.getId());

//            this.setSelectedRegionId(currentUser.getRegionId());
//            this.setSelectedRegionId(currentUser.getCityId());
//            this.setSelectedUserTypeIds(this.convertObjectListToAuthorityIDs(currentUser.getAuthorities()));
            return "/security/mip-user/update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usuário não pode ser alterado porque não foi encontrado na base de dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String update() {

        if (!this.getPassword().equals(this.getPasswordConfirmation())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Senha não foi confirmada corretamente!"));
            return "index.xhtml?faces-redirect=true";
        }

        try {
            var currentUser = userService.readById(this.getId());

            currentUser.setAccountNonExpired(!this.isAccountNonExpired());
            currentUser.setAccountNonLocked(!this.isAccountNonLocked());
            currentUser.setCredentialsNonExpired(!this.isCredentialsNonExpired());
            currentUser.setEnabled(this.isEnabled());

            currentUser.setAuthorities(this.convertAuthorityIDsToObjectList());
            currentUser.setCity(userService.readCityById(this.getSelectedCityId()));
            currentUser.setRegion(userService.readRegionById(this.getSelectedRegionId()));

            currentUser.setEmail(this.getEmail());
            currentUser.setFullName(this.getFullName());
            currentUser.setPassword(this.getPassword());
            currentUser.setUsername(this.getUsername());

            userService.update(currentUser);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Usuário alterado com sucesso!"));
            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um usuário com esse login/e-mail!"));
            return "create.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usuário não pode ser alterado porque não foram encontradas as referências para cidade, região ou tipo de usuário na base de dados!"));
            return "index.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }

    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        userService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Usuário";
    }
}
