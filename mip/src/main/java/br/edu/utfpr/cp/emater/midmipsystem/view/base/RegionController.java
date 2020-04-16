package br.edu.utfpr.cp.emater.midmipsystem.view.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.RegionService;
import br.edu.utfpr.cp.emater.midmipsystem.view.AbstractCRUDController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

// Note that there are issues to resolve when updating a Region
@Component
@RequestScope
@RequiredArgsConstructor
public class RegionController extends AbstractCRUDController<Region> {

    private final RegionService regionService;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Size(min = 5, max = 50, message = "O nome da região deve ter entre 5 e 50 caracteres")
    private String name;

    @Getter
    @Setter
    private MacroRegion macroRegion;

    @Getter
    @Setter
    private Set<City> cities;

    @Getter
    @Setter
    private List<City> selectedCities;

    @Getter
    @Setter
    private Long selectedMacroRegion;

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

    @Override
    protected void doCreate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        var newRegion = Region.builder()
                .name(this.getName())
                .macroRegion(this.regionService.readMacroRegionById(this.getSelectedMacroRegion()))
                .cities(new HashSet<City>(this.getSelectedCities()))
                .build();

        regionService.create(newRegion);
    }

    @Override
    protected void doPrepareUpdate(Long anId) throws EntityNotFoundException {
        Region existentRegion = regionService.readById(anId);
        this.setId(existentRegion.getId());
        this.setName(existentRegion.getName());
        this.setSelectedMacroRegion(existentRegion.getMacroRegion().getId());
        this.setCities(existentRegion.getCities());

        this.setSelectedCities(new ArrayList<City>(existentRegion.getCities()));
    }

    @Override
    protected void doUpdate() throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        var updatedRegion = Region.builder()
                .id(this.getId())
                .name(this.getName())
                .macroRegion(this.regionService.readMacroRegionById(this.getSelectedMacroRegion()))
                .cities(new HashSet<City>(this.getSelectedCities()))
                .build();

        regionService.update(updatedRegion);
    }

    @Override
    protected void doDelete(Long anId) throws AccessDeniedException, EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        regionService.delete(anId);
    }

    @Override
    protected String getItemName() {
        return "Região";
    }
}
