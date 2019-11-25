package br.edu.utfpr.cp.emater.midmipsystem.view.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.MIPSampleBedBugPestAnalysisService;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.MIPSampleCaterpillarPestAnalysisService;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.MIPSampleDefoliationAnalysisService;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.MIPSamplePredatorAnalysisService;
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

    private final MIPSampleCaterpillarPestAnalysisService caterpillarService;
    private final MIPSampleBedBugPestAnalysisService bedBugService;
    private final MIPSampleDefoliationAnalysisService defoliationService;
    private final MIPSamplePredatorAnalysisService predatorService;

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
    private LineChartModel defoliationChart;
    
    @Getter
    @Setter
    private LineChartModel predatorChart;
    
    @Getter
    @Setter
    private String title;
    
    @Getter
    @Setter
    private List<MacroRegion> macroRegionsAvailable;
    
    @PostConstruct
    public void init() {
        this.setTitle("Dados Estaduais");
        
        caterpillarFluctuationChart = caterpillarService.getChart();
        bedBugFluctuationChart = bedBugService.getChart();
        defoliationChart = defoliationService.getChart();
        predatorChart = predatorService.getChart();
        
        macroRegionsAvailable = caterpillarService.readAllMacroRegionsWithSurvey();
    }
    
    public void onMacroRegionSelectionChangeEvent() {

        if (this.getSelectedMacroRegionId() != null) {
            this.setTitle("Dados da Macrorregião Selecionada");
            
            var MIPSampleData = caterpillarService.readMIPSamplesByMacroRegionId(this.getSelectedMacroRegionId());
            
            this.setCaterpillarFluctuationChart(caterpillarService.getChart(MIPSampleData));
            this.setBedBugFluctuationChart(bedBugService.getChart(MIPSampleData));
            this.setDefoliationChart(defoliationService.getChart(MIPSampleData));
            this.setPredatorChart(predatorService.getChart(MIPSampleData));
            
            regionsAvailable = caterpillarService.readRegionsAvailableByMacroRegionId(this.getSelectedMacroRegionId());
        
        } else {
            this.setTitle("Dados Estaduais");
            
            this.setCaterpillarFluctuationChart(caterpillarService.getChart());
            this.setBedBugFluctuationChart(bedBugService.getChart());
            this.setDefoliationChart(defoliationService.getChart());
            this.setPredatorChart(predatorService.getChart());
        }
    }

    public void onRegionSelectionChangeEvent() {
        
        if (this.getSelectedRegionId() != null) {
            
            this.setTitle("Dados da Região Selecionada");
            
            var MIPSampleData = caterpillarService.readMIPSamplesByRegionId(this.getSelectedRegionId());
            
            this.setCaterpillarFluctuationChart(caterpillarService.getChart(MIPSampleData));
            this.setBedBugFluctuationChart(bedBugService.getChart(MIPSampleData));
            this.setDefoliationChart(defoliationService.getChart(MIPSampleData));
            this.setPredatorChart(predatorService.getChart(MIPSampleData));
            
            citiesAvailable = caterpillarService.readCitiesAvailableByRegionId(this.getSelectedRegionId());
            
        } else {
            this.setTitle("Dados Estaduais");
            
            this.setCaterpillarFluctuationChart(caterpillarService.getChart());
            this.setBedBugFluctuationChart(bedBugService.getChart());
            this.setDefoliationChart(defoliationService.getChart());
            this.setPredatorChart(predatorService.getChart());
        }
            
    }

    public void onCitySelectionChangeEvent() {
        
        if (this.getSelectedCityId() != null) {
            
            this.setTitle("Dados do Município Selecionado");
            
            var MIPSampleData = caterpillarService.readMIPSamplesByCityId(this.getSelectedCityId());
            
            this.setCaterpillarFluctuationChart(caterpillarService.getChart(MIPSampleData));
            this.setBedBugFluctuationChart(bedBugService.getChart(MIPSampleData));
            this.setDefoliationChart(defoliationService.getChart(MIPSampleData));
            this.setPredatorChart(predatorService.getChart(MIPSampleData));
            
            URsAvailable = caterpillarService.readURsAvailableByCityId(this.getSelectedCityId());
            
        } else {
            this.setTitle("Dados Estaduais");
            
            this.setCaterpillarFluctuationChart(caterpillarService.getChart());
            this.setBedBugFluctuationChart(bedBugService.getChart());
            this.setDefoliationChart(defoliationService.getChart());
            this.setPredatorChart(predatorService.getChart());
        }
    }

    public void onURSelectionChangeEvent() {
        
        if (this.getSelectedURId() != null) {
            
            var MIPSampleData = caterpillarService.readMIPSamplesByURId(this.getSelectedURId());
            
            this.setCaterpillarFluctuationChart(caterpillarService.getChart(MIPSampleData));
            this.setBedBugFluctuationChart(bedBugService.getChart(MIPSampleData));
            this.setDefoliationChart(defoliationService.getChart(MIPSampleData));
            this.setPredatorChart(predatorService.getChart(MIPSampleData));
        
        } else {
            this.setTitle("Dados Estaduais");
            
            this.setCaterpillarFluctuationChart(caterpillarService.getChart());
            this.setBedBugFluctuationChart(bedBugService.getChart());
            this.setDefoliationChart(defoliationService.getChart());
            this.setPredatorChart(predatorService.getChart());
        }
    }

}
