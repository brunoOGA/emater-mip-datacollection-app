package br.edu.utfpr.cp.emater.midmipsystem.view.security;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.security.Authority;
import br.edu.utfpr.cp.emater.midmipsystem.entity.security.MIPUser;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.view.ICRUDController;
import br.edu.utfpr.cp.emater.midmipsystem.service.security.MIPUserService;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component(value = "mipUserController")
@ViewScoped
@RequiredArgsConstructor
public class MIPUserController extends MIPUser implements ICRUDController<MIPUser> {

    private final MIPUserService userService;

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
    private List<String> userTypeSelected;

    public List<String> listUserType() {
        return List.of("Técnico", "Acesso Estadual", "Gerenciador de Usuários");
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
    
    private List<Authority> getAuthorityList(List<String> userTypeSelected) {
        
        var result = new ArrayList<Authority>();
        
        for (String item : userTypeSelected) {
            if (item.equals("Acesso Estadual"))
                    result.add(userService.readAuthorityByName("ADMIN"));
            
            if (item.equals("Gerenciador de Usuários"))
                    result.add(userService.readAuthorityByName("SYSTEM"));
        }
            
        return result;
    }

    @Override
    public String create() {
        
        if (!this.getPassword().equals(this.getPasswordConfirmation())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Senha não foi confirmada corretamente!"));
            return "create.xhtml";
        }
        
        try {
            var newUser = MIPUser.builder()
                                    .accountNonExpired(!this.isAccountNonExpired())
                                    .accountNonLocked(!this.isAccountNonLocked())
                                    .city(userService.readCityById(this.getSelectedCityId()))
                                    .credentialsNonExpired(!this.isCredentialsNonExpired())
                                    .email(this.getEmail())
                                    .enabled(!this.isEnabled())
                                    .fullName(this.getFullName())
                                    .password(this.getPassword())
                                    .region(userService.readRegionById(this.getSelectedRegionId()))
                                    .username(this.getUsername())
                                    .authorities(this.getAuthorityList(this.getUserTypeSelected()))
                                 .build();
                    
//            userService.create(newUser);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Unidade de referência criada com sucesso!"));
            return "index.xhtml";
            
//        } catch (EntityAlreadyExistsException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe uma unidade de referência com esse nome, nessa cidade para esse produtor!"));
//            return "create.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Unidade de referência não pode ser criada porque não foram encontradas as referências para cidade, produtor ou responsável técnico na base de dados!"));
            return "index.xhtml";
//
//        } catch (AnyPersistenceException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
//            return "index.xhtml";
        }
    }

//    @Override
//    public String prepareUpdate(Long anId) {
//
//        try {
//            Field existentField = userService.readById(anId);
//            this.setId(existentField.getId());
//            this.setName(existentField.getName());
//            this.setLocation(existentField.getLocation());
//            this.setSelectedCityId(existentField.getCityId());
//            this.setSelectedFarmerId(existentField.getFarmerId());
//            this.setSelectedSupervisorIds(existentField.getSupervisors().stream().map(Supervisor::getId).collect(Collectors.toList()));
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
//
//        try {
//            var updatedField = Field.builder()
//                    .id(this.getId())
//                    .name(this.getName())
//                    .location(this.getLocation())
//                    .city(this.userService.readCityById(this.getSelectedCityId()))
//                    .farmer(this.userService.readFarmerById(this.getSelectedFarmerId()))
//                    .supervisors(userService.readSupervisorsByIds(this.getSelectedSupervisorIds()))
//                    .build();
//
//            userService.update(updatedField);
//
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Unidade de referência alterada"));
//            return "index.xhtml";
//
//        } catch (SupervisorNotAllowedInCity ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Um ou mais responsáveis técnicos selecionados não atendem a cidade selecionada para essa UR!"));
//            return "update.xhtml";
//
//        } catch (EntityAlreadyExistsException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe uma unidade de referência com esse nome, nessa cidade para esse produtor!"));
//            return "update.xhtml";
//
//        } catch (EntityNotFoundException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Unidade de referência não pode ser alterada porque não foi encontrada na base de dados!"));
//            return "update.xhtml";
//
//        } catch (AnyPersistenceException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
//            return "index.xhtml";
//        }
//
//    }
//
    public String delete(Long anId) {

        try {
            userService.delete(anId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Usuário excluído!"));
            return "index.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usuário não pode ser excluído porque não foi encontrado na base de dados!"));
            return "index.xhtml";

        } catch (EntityInUseException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usuário não pode ser excluído porque está sendo usado no sistema!"));
            return "index.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String prepareUpdate(Long anId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
