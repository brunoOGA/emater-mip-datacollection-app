package br.edu.utfpr.cp.emater.midmipsystem.view.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.security.Authority;
import br.edu.utfpr.cp.emater.midmipsystem.entity.security.MIPUserPrincipal;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.AnalysisService;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.chart.DAEAndOccurrenceDTO;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.SummaryBoardDTO;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.SummaryBoardService;
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

    private final SummaryBoardService summaryBoardService;
    private final AnalysisService analysisService;

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

    @PostConstruct
    public void init() {

        var loggedUser = ((MIPUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        if (loggedUser.getRegion() != null) {
            var summaryBoard = summaryBoardService.getSummaryBoardData(loggedUser.getRegion());

            this.setSummaryBoardData(summaryBoard);
            this.setCurrentRegionName(loggedUser.getRegionName());
            this.setSummaryBoardRegion(summaryBoardService.getSummaryBoardRegionSummary(summaryBoard));

            if (loggedUser.getAuthorities().stream().mapToLong(Authority::getId).anyMatch(id -> id == 1)) {
                this.setTitle("Dados Estaduais");

                var MIPSampleData = analysisService.readSamples();

                this.setCaterpillarFluctuationChart(this.analysisService.getCaterpillarChart(MIPSampleData));
                this.setBedBugFluctuationChart(this.analysisService.getBedBugChart(MIPSampleData));
                this.setPredatorChart(this.analysisService.getNaturalPredatorChart(MIPSampleData));
                this.setDefoliationChart(this.analysisService.getDefoliationChart(MIPSampleData));

            } else {
                var MIPSampleData = analysisService.readMIPSamplesByMIPUser(loggedUser);

                this.setTitle(String.format("Dados das Propriedades Gerenciadas por %s", loggedUser.getFullName()));

                this.setCaterpillarFluctuationChart(this.analysisService.getCaterpillarChart(MIPSampleData));
                this.setBedBugFluctuationChart(this.analysisService.getBedBugChart(MIPSampleData));
                this.setPredatorChart(this.analysisService.getNaturalPredatorChart(MIPSampleData));
                this.setDefoliationChart(this.analysisService.getDefoliationChart(MIPSampleData));
            }

            macroRegionsAvailable = analysisService.readAllMacroRegionsWithSurvey();
        }

    }

    public void onMacroRegionSelectionChangeEvent() {

        if (this.getSelectedMacroRegionId() != null) {

            var macroRegion = analysisService.readMacroRegionById(this.getSelectedMacroRegionId());

            this.setTitle(String.format("Dados da Macrorregião %s", macroRegion.map(MacroRegion::getName).orElse("Selecionada")));

            var MIPSampleData = analysisService.readMIPSamplesByMacroRegionId(this.getSelectedMacroRegionId());

            this.setCaterpillarFluctuationChart(this.analysisService.getCaterpillarChart(MIPSampleData));
            this.setBedBugFluctuationChart(this.analysisService.getBedBugChart(MIPSampleData));
            this.setPredatorChart(this.analysisService.getNaturalPredatorChart(MIPSampleData));
            this.setDefoliationChart(this.analysisService.getDefoliationChart(MIPSampleData));

            regionsAvailable = analysisService.readRegionsAvailableByMacroRegionId(this.getSelectedMacroRegionId());

        } else {

            this.setTitle("Dados Estaduais");

            var MIPSampleData = analysisService.readSamples();

            this.setCaterpillarFluctuationChart(this.analysisService.getCaterpillarChart(MIPSampleData));
            this.setBedBugFluctuationChart(this.analysisService.getBedBugChart(MIPSampleData));
            this.setPredatorChart(this.analysisService.getNaturalPredatorChart(MIPSampleData));
            this.setDefoliationChart(this.analysisService.getDefoliationChart(MIPSampleData));
        }
    }

    public void onRegionSelectionChangeEvent() {

        if (this.getSelectedRegionId() != null) {

            var region = analysisService.readRegionById(this.getSelectedRegionId());

            this.setTitle(String.format("Dados da Região %s", region.map(Region::getName).orElse("Selecionada")));

            var MIPSampleData = analysisService.readMIPSamplesByRegionId(this.getSelectedRegionId());

            this.setCaterpillarFluctuationChart(this.analysisService.getCaterpillarChart(MIPSampleData));
            this.setBedBugFluctuationChart(this.analysisService.getBedBugChart(MIPSampleData));
            this.setPredatorChart(this.analysisService.getNaturalPredatorChart(MIPSampleData));
            this.setDefoliationChart(this.analysisService.getDefoliationChart(MIPSampleData));

            citiesAvailable = analysisService.readCitiesAvailableByRegionId(this.getSelectedRegionId());

        } else {

            var MIPSampleData = analysisService.readSamples();

            this.setCaterpillarFluctuationChart(this.analysisService.getCaterpillarChart(MIPSampleData));
            this.setBedBugFluctuationChart(this.analysisService.getBedBugChart(MIPSampleData));
            this.setPredatorChart(this.analysisService.getNaturalPredatorChart(MIPSampleData));
            this.setDefoliationChart(this.analysisService.getDefoliationChart(MIPSampleData));
        }

    }

    public void onCitySelectionChangeEvent() {

        if (this.getSelectedCityId() != null) {

            var city = analysisService.readCityById(this.getSelectedCityId());

            this.setTitle(String.format("Dados do Município %s", city.map(City::getName).orElse("Selecionado")));

            var MIPSampleData = analysisService.readMIPSamplesByCityId(this.getSelectedCityId());

            this.setCaterpillarFluctuationChart(this.analysisService.getCaterpillarChart(MIPSampleData));
            this.setBedBugFluctuationChart(this.analysisService.getBedBugChart(MIPSampleData));
            this.setPredatorChart(this.analysisService.getNaturalPredatorChart(MIPSampleData));
            this.setDefoliationChart(this.analysisService.getDefoliationChart(MIPSampleData));

            URsAvailable = analysisService.readURsAvailableByCityId(this.getSelectedCityId());

        } else {

            var MIPSampleData = analysisService.readSamples();

            this.setCaterpillarFluctuationChart(this.analysisService.getCaterpillarChart(MIPSampleData));
            this.setBedBugFluctuationChart(this.analysisService.getBedBugChart(MIPSampleData));
            this.setPredatorChart(this.analysisService.getNaturalPredatorChart(MIPSampleData));
            this.setDefoliationChart(this.analysisService.getDefoliationChart(MIPSampleData));
        }
    }

    public void onURSelectionChangeEvent() {

        if (this.getSelectedURId() != null) {

            var ur = analysisService.readFieldById(this.getSelectedURId());

            this.setTitle(String.format("Dados da UR %s", ur.map(Field::getName).orElse("Selecionada")));

            var MIPSampleData = analysisService.readMIPSamplesByURId(this.getSelectedURId());

            this.setCaterpillarFluctuationChart(this.analysisService.getCaterpillarChart(MIPSampleData));
            this.setBedBugFluctuationChart(this.analysisService.getBedBugChart(MIPSampleData));
            this.setPredatorChart(this.analysisService.getNaturalPredatorChart(MIPSampleData));
            this.setDefoliationChart(this.analysisService.getDefoliationChart(MIPSampleData));

        } else {

            var MIPSampleData = analysisService.readSamples();

            this.setCaterpillarFluctuationChart(this.analysisService.getCaterpillarChart(MIPSampleData));
            this.setBedBugFluctuationChart(this.analysisService.getBedBugChart(MIPSampleData));
            this.setPredatorChart(this.analysisService.getNaturalPredatorChart(MIPSampleData));
            this.setDefoliationChart(this.analysisService.getDefoliationChart(MIPSampleData));
        }
    }

}
