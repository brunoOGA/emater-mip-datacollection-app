package br.edu.utfpr.cp.emater.midmipsystem.view.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestNaturalPredator;
import br.edu.utfpr.cp.emater.midmipsystem.entity.security.Authority;
import br.edu.utfpr.cp.emater.midmipsystem.entity.security.MIPUserPrincipal;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.DAEAndOccurrenceDTO;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.MIPSampleBedBugPestAnalysisService;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.MIPSampleCaterpillarPestAnalysisService;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.MIPSampleDefoliationAnalysisService;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.MIPSamplePredatorAnalysisService;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.SummaryBoardDTO;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.SummaryBoardService;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.chart.MIPNaturalPredatorChartGeneratorService;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.chart.MIPPestChartGeneratorService;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("dashboardController")
@ViewScoped
@RequiredArgsConstructor
public class DashboardController implements Serializable {
    
    private final MIPSampleCaterpillarPestAnalysisService caterpillarService;
    private final MIPSampleBedBugPestAnalysisService bedBugService;
    private final MIPSampleDefoliationAnalysisService defoliationService;
    private final MIPSamplePredatorAnalysisService predatorService;
    private final SummaryBoardService summaryBoardService;
    
    private final MIPSampleService mipSampleService;
    
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
    private Map<String, List<DAEAndOccurrenceDTO>> caterpillarFluctuationChart;
    
    @Getter
    @Setter
    private Map<String, List<DAEAndOccurrenceDTO>> bedBugFluctuationChart;
    
    @Getter
    @Setter
    private List<DAEAndOccurrenceDTO> defoliationChart;
    
    @Getter
    @Setter
    private Map<String, List<DAEAndOccurrenceDTO>> predatorChart;
    
    @Getter
    @Setter
    private String title;
    
    @Getter
    @Setter
    private List<MacroRegion> macroRegionsAvailable;
    
    @Getter
    @Setter
    private List<SummaryBoardDTO> summaryBoardData;
    
    @Getter
    @Setter
    private String currentRegionName;
    
    @Getter
    @Setter
    private SummaryBoardDTO summaryBoardRegion;
    
    @Getter
    @Setter
    private List<Pest> caterpillarTargetList;
    
    @Getter
    @Setter
    private List<Pest> bedBugTargetList;
    
    @Getter
    @Setter
    private List<PestNaturalPredator> predatorTargetList;
    
    private void setTargetLists() {
        
        this.setCaterpillarTargetList(List.of(
                this.mipSampleService.readPestById(1L).get(),
                this.mipSampleService.readPestById(2L).get(),
                this.mipSampleService.readPestById(3L).get(),
                this.mipSampleService.readPestById(4L).get(),
                this.mipSampleService.readPestById(5L).get(),
                this.mipSampleService.readPestById(6L).get(),
                this.mipSampleService.readPestById(7L).get(),
                this.mipSampleService.readPestById(8L).get()));
        
        this.setBedBugTargetList(List.of(
                this.mipSampleService.readPestById(9L).get(),
                this.mipSampleService.readPestById(10L).get(),
                this.mipSampleService.readPestById(11L).get(),
                this.mipSampleService.readPestById(12L).get(),
                this.mipSampleService.readPestById(13L).get(),
                this.mipSampleService.readPestById(14L).get(),
                this.mipSampleService.readPestById(15L).get(),
                this.mipSampleService.readPestById(16L).get(),
                this.mipSampleService.readPestById(17L).get(),
                this.mipSampleService.readPestById(18L).get()));
        
        this.setPredatorTargetList(List.of(
                this.mipSampleService.readPredatorById(1L).get(),
                this.mipSampleService.readPredatorById(2L).get(),
                this.mipSampleService.readPredatorById(3L).get(),
                this.mipSampleService.readPredatorById(4L).get(),
                this.mipSampleService.readPredatorById(5L).get(),
                this.mipSampleService.readPredatorById(6L).get(),
                this.mipSampleService.readPredatorById(7L).get(),
                this.mipSampleService.readPredatorById(8L).get(),
                this.mipSampleService.readPredatorById(9L).get(),
                this.mipSampleService.readPredatorById(10L).get(),
                this.mipSampleService.readPredatorById(11L).get(),
                this.mipSampleService.readPredatorById(12L).get()));
    }
    
    @PostConstruct
    public void init() {
        
        this.setTargetLists();
        
        var loggedUser = ((MIPUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        
        var summaryBoard = summaryBoardService.getSummaryBoardData(loggedUser.getRegion());
        
        this.setSummaryBoardData(summaryBoard);
        this.setCurrentRegionName(loggedUser.getRegionName());
        this.setSummaryBoardRegion(summaryBoardService.getSummaryBoardRegionSummary(summaryBoard));
        
        if (loggedUser.getAuthorities().stream().mapToLong(Authority::getId).anyMatch(id -> id == 1)) {
            this.setTitle("Dados Estaduais");
            
            caterpillarFluctuationChart = caterpillarService.getChart();
            bedBugFluctuationChart = bedBugService.getChart();
            defoliationChart = defoliationService.getChart();
            predatorChart = predatorService.getChart();
            
        } else {
            var MIPSampleData = caterpillarService.readMIPSamplesByMIPUser(loggedUser);
            
            this.setTitle(String.format("Dados das Propriedades Gerenciadas por %s", loggedUser.getFullName()));
            
            var caterpillarChartData = new MIPPestChartGeneratorService().of(MIPSampleData, this.getCaterpillarTargetList());
            var bedBugChartData = new MIPPestChartGeneratorService().of(MIPSampleData, this.getBedBugTargetList());
            var predatorChartData = new MIPNaturalPredatorChartGeneratorService().of(MIPSampleData, this.getPredatorTargetList());
            
            this.setCaterpillarFluctuationChart(caterpillarChartData);
            this.setBedBugFluctuationChart(bedBugChartData);
            this.setPredatorChart(predatorChartData);
            
//            this.setCaterpillarFluctuationChart(caterpillarService.getChart(MIPSampleData));
//            this.setBedBugFluctuationChart(bedBugService.getChart(MIPSampleData));
            this.setDefoliationChart(defoliationService.getChart(MIPSampleData));
//            this.setPredatorChart(predatorService.getChart(MIPSampleData));
        }
        
        macroRegionsAvailable = caterpillarService.readAllMacroRegionsWithSurvey();
    }
    
    public void onMacroRegionSelectionChangeEvent() {
        
        if (this.getSelectedMacroRegionId() != null) {
            
            var macroRegion = caterpillarService.readMacroRegionById(this.getSelectedMacroRegionId());
            
            this.setTitle(String.format("Dados da Macrorregião %s", macroRegion.map(MacroRegion::getName).orElse("Selecionada")));
            
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
            
            var region = caterpillarService.readRegionById(this.getSelectedRegionId());
            
            this.setTitle(String.format("Dados da Região %s", region.map(Region::getName).orElse("Selecionada")));
            
            var MIPSampleData = caterpillarService.readMIPSamplesByRegionId(this.getSelectedRegionId());
            
            this.setCaterpillarFluctuationChart(caterpillarService.getChart(MIPSampleData));
            this.setBedBugFluctuationChart(bedBugService.getChart(MIPSampleData));
            this.setDefoliationChart(defoliationService.getChart(MIPSampleData));
            this.setPredatorChart(predatorService.getChart(MIPSampleData));
            
            citiesAvailable = caterpillarService.readCitiesAvailableByRegionId(this.getSelectedRegionId());
            
        } else {
            
            this.setCaterpillarFluctuationChart(caterpillarService.getChart());
            this.setBedBugFluctuationChart(bedBugService.getChart());
            this.setDefoliationChart(defoliationService.getChart());
            this.setPredatorChart(predatorService.getChart());
        }
        
    }
    
    public void onCitySelectionChangeEvent() {
        
        if (this.getSelectedCityId() != null) {
            
            var city = caterpillarService.readCityById(this.getSelectedCityId());
            
            this.setTitle(String.format("Dados do Município %s", city.map(City::getName).orElse("Selecionado")));
            
            var MIPSampleData = caterpillarService.readMIPSamplesByCityId(this.getSelectedCityId());
            
            this.setCaterpillarFluctuationChart(caterpillarService.getChart(MIPSampleData));
            this.setBedBugFluctuationChart(bedBugService.getChart(MIPSampleData));
            this.setDefoliationChart(defoliationService.getChart(MIPSampleData));
            this.setPredatorChart(predatorService.getChart(MIPSampleData));
            
            URsAvailable = caterpillarService.readURsAvailableByCityId(this.getSelectedCityId());
            
        } else {
            
            this.setCaterpillarFluctuationChart(caterpillarService.getChart());
            this.setBedBugFluctuationChart(bedBugService.getChart());
            this.setDefoliationChart(defoliationService.getChart());
            this.setPredatorChart(predatorService.getChart());
        }
    }
    
    public void onURSelectionChangeEvent() {
        
        if (this.getSelectedURId() != null) {
            
            var ur = caterpillarService.readFieldById(this.getSelectedURId());
            
            this.setTitle(String.format("Dados da UR %s", ur.map(Field::getName).orElse("Selecionada")));
            
            var MIPSampleData = caterpillarService.readMIPSamplesByURId(this.getSelectedURId());
            
            this.setCaterpillarFluctuationChart(caterpillarService.getChart(MIPSampleData));
            this.setBedBugFluctuationChart(bedBugService.getChart(MIPSampleData));
            this.setDefoliationChart(defoliationService.getChart(MIPSampleData));
            this.setPredatorChart(predatorService.getChart(MIPSampleData));
            
        } else {
            
            this.setCaterpillarFluctuationChart(caterpillarService.getChart());
            this.setBedBugFluctuationChart(bedBugService.getChart());
            this.setDefoliationChart(defoliationService.getChart());
            this.setPredatorChart(predatorService.getChart());
        }
    }
    
}
