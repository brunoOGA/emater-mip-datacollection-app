package br.edu.utfpr.cp.emater.midmipsystem.view.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.RegionService;
import br.edu.utfpr.cp.emater.midmipsystem.view.ICRUDController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
public class RegionController extends Region implements ICRUDController<Region> {

    private final RegionService regionService;

    @Getter
    @Setter
    private List<City> selectedCities;

    @Autowired
    public RegionController(RegionService aRegionService) {
        this.regionService = aRegionService;
    }

    @Override
    public List<Region> readAll() {
        return regionService.readAll();
    }

    public List<MacroRegion> readAllMacroRegions() {
        return regionService.readAllMacroRegions();
    }

    public List<City> readAllCitiesWithoutRegion() {
        return regionService.readAllCitiesWithoutRegion();
    }

    public List<City> readAllCitiesForUpdate() {
        var allCitiesWithoutRegion = this.readAllCitiesWithoutRegion();
        var citiesInThisRegion = this.getCities();

        ArrayList<City> allCities = new ArrayList<City>(allCitiesWithoutRegion);
        allCities.addAll(citiesInThisRegion);

        return allCities;
    }

    private boolean checkCityWasSelected() {
        return (this.getSelectedCities() == null || this.getSelectedCities().size() == 0);
    }

    @Override
    public String create() {

        if (this.checkCityWasSelected()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Uma região deve possuir pelo menos uma cidade"));
            return "create.xhtml";
        }

        var newRegion = Region.builder().name(this.getName()).macroRegion(this.getMacroRegion()).build();
        newRegion.setCities(new HashSet<City>(this.getSelectedCities()));

        try {
            regionService.create(newRegion);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", String.format("Região [%s] criada com sucesso!", newRegion.getName())));
            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe uma região com esse nome nessa macrorregião! Use um nome diferente, ou selecione outra macrorregião."));
            return "create.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "A região não pode ser criada porque a cidade ou a macrorregião não foram encontradas na base de dados!"));
            return "create.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String prepareUpdate(Long anId) {

        try {
            Region existentRegion = regionService.readById(anId);
            this.setId(existentRegion.getId());
            this.setName(existentRegion.getName());
            this.setMacroRegion(existentRegion.getMacroRegion());
//            this.setCities(new HashSet<City>());
            this.setCities(existentRegion.getCities());

            this.setSelectedCities(new ArrayList<City>(existentRegion.getCities()));
//              this.setSelectedCities(new ArrayList<>());

            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Região não pode ser alterada porque não foi encontrada na base de dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String update() {

        if (this.checkCityWasSelected()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Uma região deve possuir pelo menos uma cidade"));
            return "update.xhtml";
        }

        var updatedRegion = Region.builder().id(this.getId()).name(this.getName()).macroRegion(this.getMacroRegion()).build();
        updatedRegion.setCities(new HashSet<City>(this.getSelectedCities()));

        try {
            regionService.update(updatedRegion);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Região alterada!"));
            return "index.xhtml";

        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe uma região com esse nome! Use um nome diferente."));
            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Região não pode ser alterada porque não foi encontrada na base de dados!"));
            return "update.xhtml";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
            return "index.xhtml";
        }

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
