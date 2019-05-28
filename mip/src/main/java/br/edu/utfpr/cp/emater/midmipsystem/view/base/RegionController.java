package br.edu.utfpr.cp.emater.midmipsystem.view.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.RegionService;
import br.edu.utfpr.cp.emater.midmipsystem.view.ICRUDController;
import java.util.ArrayList;
import java.util.HashSet;
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

// Note that there are issues to resolve when updating a Region
@Component
@RequestScope
public class RegionController extends Region implements ICRUDController<Region> {

    private final RegionService regionService;

    @Getter
    @Setter
    private List<City> selectedCities;
//    private String selectedCities[];

    @Getter
    @Setter
    private Long selectedMacroRegion;

    @Autowired
    public RegionController(RegionService aRegionService) {
        this.regionService = aRegionService;
        this.selectedCities = new ArrayList<>();
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

        if (this.getCities() != null) {

            var citiesInThisRegion = this.getCities();
            var allCities = new ArrayList<City>(allCitiesWithoutRegion);
            allCities.addAll(citiesInThisRegion);

            return allCities;

        } else {
            return allCitiesWithoutRegion;
        }

    }

//    private Set<Long> convertStringCitiesIdToSetId() {
//
//        String stringIDs[] = this.getSelectedCities().split(",");
//
//        var result = new HashSet<Long>();
//
//        for (String currentStringID : stringIDs) {
//            result.add(new Long(currentStringID));
//        }
//
//        return result;
//    }
//    private Set<City> retrieveCities(Set<Long> ids) throws EntityNotFoundException {
//
//        var result = new HashSet<City>();
//
//        var allCityEntities = regionService.readAllCities();
//
//        for (Long currentId : ids) {
//            result.add(
//                    allCityEntities
//                            .stream()
//                            .filter(
//                                    currentCity -> currentCity
//                                            .getId().equals(currentId))
//                            .findAny()
//                            .orElseThrow(EntityNotFoundException::new));
//        }
//
//        return result;
//    }
    @Override
    public String create() {

        try {
            var newRegion = Region.builder()
                    .name(this.getName())
                    //                    .macroRegion(this.getMacroRegion())
                    .macroRegion(this.regionService.readMacroRegionById(this.getSelectedMacroRegion()))
                    //                    .cities(retrieveCities(this.convertStringCitiesIdToSetId()))
                    .cities(new HashSet<City>(this.getSelectedCities()))
                    .build();

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

//    private String convertEntityCitiesToStringId(Set<City> cities) {
//
//        var idList = cities.stream().map(City::getId).collect(Collectors.toList());
//        var count = idList.size() - 1;
//
//        var idListStringBuilder = new StringBuilder();
//
//        for (Long currentId : idList) {
//            idListStringBuilder.append(String.valueOf(currentId));
//
//            if (count > 0) {
//                idListStringBuilder.append(",");
//                count--;
//            }
//        }
//
//        return idListStringBuilder.toString();
//    }
    @Override
    public String prepareUpdate(Long anId) {

        try {
            Region existentRegion = regionService.readById(anId);
            this.setId(existentRegion.getId());
            this.setName(existentRegion.getName());
//            this.setMacroRegion(existentRegion.getMacroRegion());
            this.setSelectedMacroRegion(existentRegion.getMacroRegion().getId());
            this.setCities(existentRegion.getCities());

//            this.setSelectedCities(convertEntityCitiesToStringId(existentRegion.getCities()));
            this.setSelectedCities(new ArrayList<City>(existentRegion.getCities()));

            return "update.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Região não pode ser alterada porque não foi encontrada na base de dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String update() {

        try {
            var updatedRegion = Region.builder()
                    .id(this.getId())
                    .name(this.getName())
                    //                    .macroRegion(this.getMacroRegion())
                    .macroRegion(this.regionService.readMacroRegionById(this.getSelectedMacroRegion()))
                    //                    .cities(retrieveCities(this.convertStringCitiesIdToSetId()))
                    .cities(new HashSet<City>(this.getSelectedCities()))
                    .build();

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

        try {
            Region existentRegion = regionService.readById(anId);
            this.setId(existentRegion.getId());
            this.setName(existentRegion.getName());

            return "delete.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Região não pode ser excluída porque não foi encontrada na base de dados!"));
            return "index.xhtml";
        }
    }

    @Override
    public String delete() {

        try {
            regionService.delete(this.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", String.format("Região [%s] excluída!", this.getName())));

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Região não pode ser excluída porque não foi encontrada na base de dados!"));

        } catch (EntityInUseException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Região não pode ser excluída porque está sendo usada por uma propriedade!"));

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
        }

        return "index.xhtml";
    }

}
