package br.edu.utfpr.cp.emater.midmipsystem.view.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.MIPPestAnalysisService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.primefaces.component.linechart.LineChart;
import org.primefaces.model.chart.LineChartModel;
import org.springframework.stereotype.Component;

@Component("dashboardController")
@ViewScoped
@RequiredArgsConstructor
public class DashboardController implements Serializable {

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
    
    @Getter
    @Setter
    private String title;

    @PostConstruct
    public void init() {
        this.setTitle("Dados Estaduais");
//        pestFluctuationChart = analysisService.getPestFluctuationChart();
        pestFluctuationChart = analysisService.pestOccurrenceLineChart();

    }

    public List<MacroRegion> getMacroRegionsAvailable() {
        return analysisService.readAllMacroRegions();
    }

    public void onMacroRegionSelectionChangeEvent() {

//        if (this.getSelectedMacroRegionId() != null) {
//            this.setTitle("Dados da Macrorregião Selecionada");
//            this.setPestFluctuationChart(analysisService.getPestFluctuationChartForMacroRegion(selectedMacroRegionId));
//            regionsAvailable = analysisService.getRegionsAvailableFor(this.getSelectedMacroRegionId());
//        
//        } else {
//            this.setTitle("Dados Estaduais");
//            this.setPestFluctuationChart(analysisService.getPestFluctuationChart());
//        }
    }

    public void onRegionSelectionChangeEvent() {
        
//        if (this.getSelectedRegionId() != null) {
//            this.setTitle("Dados da Região Selecionada");
//            citiesAvailable = analysisService.getCitiesAvailableFor(this.getSelectedRegionId());
//
//            try {
//                this.setPestFluctuationChart(analysisService.getPestFluctuationChartForRegion(selectedRegionId));
//            
//            } catch (EntityNotFoundException ex) {
//                this.setPestFluctuationChart(new LineChartModel());
//            }
//            
//        } else {
//            this.setTitle("Dados Estaduais");
//            this.setPestFluctuationChart(analysisService.getPestFluctuationChart());
//        }
            
    }

    public void onCitySelectionChangeEvent() {
//        if (this.getSelectedCityId() != null) {
//            this.setTitle("Dados do Município Selecionado");
//            URsAvailable = analysisService.getURsAvailableFor(this.getSelectedCityId());
//            this.setPestFluctuationChart(analysisService.getPestFluctuationChartForCity(this.getSelectedCityId()));
//        } else {
//            this.setTitle("Dados Estaduais");
//            this.setPestFluctuationChart(analysisService.getPestFluctuationChart());
//        }
    }

    public void onURSelectionChangeEvent() {
//        if (this.getSelectedURId() != null) {
//            this.setTitle("Dados da UR Selecionada"); 
//            this.setPestFluctuationChart(analysisService.getPestFluctuationChartForUR(this.getSelectedURId()));
//        
//        } else {
//            this.setTitle("Dados Estaduais");
//            this.setPestFluctuationChart(analysisService.getPestFluctuationChart());
//        }
    }

}
