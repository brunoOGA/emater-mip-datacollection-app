package br.edu.utfpr.cp.emater.midmipsystem.view.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.MIPPestAnalysisService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.primefaces.model.chart.LineChartModel;
import org.springframework.stereotype.Component;


@Component ("dashboardController")
@ViewScoped
@RequiredArgsConstructor
public class DashboardController implements Serializable{

    private final MIPPestAnalysisService analysisService;

    @Getter
    @Setter
    private Long selectedMacroRegionId;
    
    @Getter
    @Setter
    private List<Region> regionsAvailable;
    
    @Getter
    @Setter
    private Long selectedRegionId;
    
    @Getter
    @Setter
    private List<City> citiesAvailable;
    
    @Getter
    @Setter
    private Long selectedCityId;
    
    @Getter
    @Setter
    private List<Field> URsAvailable;
    
    @Getter
    @Setter
    private Long selectedURId;
    
    @Getter
    @Setter
    private LineChartModel pestFluctuationChart;
    
    @PostConstruct // Check whether this is a source for bugs
    public void init() {
        pestFluctuationChart = analysisService.getPestFluctuationChart();
    }
    
    public List<MacroRegion> getMacroRegionsAvailable() {
        return analysisService.readAllMacroRegions();
    }
    
    public void onMacroRegionSelectionChangeEvent() {
        
        if (this.getSelectedMacroRegionId() != null) {
            this.setPestFluctuationChart(analysisService.getPestFluctuationChartForMacroRegion(selectedMacroRegionId));
            regionsAvailable = analysisService.getRegionsAvailableFor (this.getSelectedMacroRegionId());
        }
    }
    
    public void onRegionSelectionChangeEvent() {
        if (this.getSelectedRegionId() != null)
            citiesAvailable = analysisService.getCitiesAvailableFor (this.getSelectedRegionId());
            
    }
    
    public void onCitySelectionChangeEvent() {
        if (this.getSelectedCityId() != null)
            URsAvailable = analysisService.getURsAvailableFor (this.getSelectedCityId());
    }
    
    public void updateCharts() {
        if (this.getSelectedURId() != null)
            this.setPestFluctuationChart(analysisService.getPestFluctuationChartForUR(this.getSelectedURId()));
    }

}
