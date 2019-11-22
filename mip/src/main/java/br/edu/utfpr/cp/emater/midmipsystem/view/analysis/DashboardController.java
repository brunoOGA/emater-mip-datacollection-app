package br.edu.utfpr.cp.emater.midmipsystem.view.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.MIPBedBugAnalysisService;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.MIPCaterpillarAnalysisService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.primefaces.model.chart.LineChartModel;
import org.springframework.stereotype.Component;

@Component("dashboardController")
@ViewScoped
@RequiredArgsConstructor
public class DashboardController implements Serializable {

    private final MIPCaterpillarAnalysisService caterpillarService;
    private final MIPBedBugAnalysisService bedBugService;

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
    private LineChartModel caterpillarFluctuationChart;
    
    @Getter
    @Setter
    private LineChartModel bedBugFluctuationChart;
    
    @Getter
    @Setter
    private String title;
    
    @Getter
    @Setter
    private List<MacroRegion> macroRegionsAvailable;
    
    @PostConstruct
    public void init() {
        this.setTitle("Dados Estaduais");
        
        caterpillarFluctuationChart = caterpillarService.getPestLineChart();
        bedBugFluctuationChart = bedBugService.getPestLineChart();
        
        macroRegionsAvailable = caterpillarService.readAllMacroRegionsWithSurvey();
    }
    
    public void onMacroRegionSelectionChangeEvent() {

        if (this.getSelectedMacroRegionId() != null) {
            this.setTitle("Dados da Macrorregião Selecionada");
            
            var MIPSampleData = caterpillarService.getMIPSamplesByMacroRegionId(this.getSelectedMacroRegionId());
            
            this.setCaterpillarFluctuationChart(caterpillarService.getPestLineChart(MIPSampleData));
            this.setBedBugFluctuationChart(bedBugService.getPestLineChart(MIPSampleData));
            
            regionsAvailable = caterpillarService.getRegionsAvailableFor(this.getSelectedMacroRegionId());
        
        } else {
            this.setTitle("Dados Estaduais");
            
            this.setCaterpillarFluctuationChart(caterpillarService.getPestLineChart());
            this.setBedBugFluctuationChart(bedBugService.getPestLineChart());
        }
    }

    public void onRegionSelectionChangeEvent() {
        
        if (this.getSelectedRegionId() != null) {
            
            this.setTitle("Dados da Região Selecionada");
            
            var MIPSampleData = caterpillarService.getMIPSamplesByRegionId(this.getSelectedRegionId());
            
            this.setCaterpillarFluctuationChart(caterpillarService.getPestLineChart(MIPSampleData));
            this.setBedBugFluctuationChart(bedBugService.getPestLineChart(MIPSampleData));
            
            citiesAvailable = caterpillarService.getCitiesAvailableFor(this.getSelectedRegionId());
            
        } else {
            this.setTitle("Dados Estaduais");
            
            this.setCaterpillarFluctuationChart(caterpillarService.getPestLineChart());
            this.setBedBugFluctuationChart(bedBugService.getPestLineChart());
        }
            
    }

    public void onCitySelectionChangeEvent() {
        
        if (this.getSelectedCityId() != null) {
            
            this.setTitle("Dados do Município Selecionado");
            
            var MIPSampleData = caterpillarService.getMIPSamplesByCityId(this.getSelectedCityId());
            
            this.setCaterpillarFluctuationChart(caterpillarService.getPestLineChart(MIPSampleData));
            this.setBedBugFluctuationChart(bedBugService.getPestLineChart(MIPSampleData));
            
            URsAvailable = caterpillarService.getURsAvailableFor(this.getSelectedCityId());
            
        } else {
            this.setTitle("Dados Estaduais");
            
            this.setCaterpillarFluctuationChart(caterpillarService.getPestLineChart());
            this.setBedBugFluctuationChart(bedBugService.getPestLineChart());
        }
    }

    public void onURSelectionChangeEvent() {
        
        if (this.getSelectedURId() != null) {
            
            var MIPSampleData = caterpillarService.getMIPSamplesByURId(this.getSelectedURId());
            
            this.setCaterpillarFluctuationChart(caterpillarService.getPestLineChart(MIPSampleData));
            this.setBedBugFluctuationChart(bedBugService.getPestLineChart(MIPSampleData));
        
        } else {
            this.setTitle("Dados Estaduais");
            
            this.setCaterpillarFluctuationChart(caterpillarService.getPestLineChart());
            this.setBedBugFluctuationChart(bedBugService.getPestLineChart());
        }
    }

}
