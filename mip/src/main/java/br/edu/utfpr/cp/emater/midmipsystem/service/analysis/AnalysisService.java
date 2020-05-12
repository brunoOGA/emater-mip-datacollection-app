package br.edu.utfpr.cp.emater.midmipsystem.service.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.chart.DAEAndOccurrenceDTO;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestNaturalPredator;
import br.edu.utfpr.cp.emater.midmipsystem.entity.security.MIPUser;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.chart.DefoliationChartService;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.chart.NaturalPredatorMIPSampleChartService;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.chart.PestMIPSampleChartService;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AnalysisService {
    
    @Getter
    private final MIPSampleService mipSampleService;
    
    private final SurveyService surveyService;
    
    public Map<String, List<DAEAndOccurrenceDTO>> getCaterpillarChart(List<MIPSample> aSampleList) {
        return new PestMIPSampleChartService().of(aSampleList, this.getCaterpillarTargetList());
    }
    
    public Map<String, List<DAEAndOccurrenceDTO>> getBedBugChart(List<MIPSample> aSampleList) {
        return new PestMIPSampleChartService().of(aSampleList, this.getBedBugTargetList());
    }
    
    public Map<String, List<DAEAndOccurrenceDTO>> getNaturalPredatorChart(List<MIPSample> aSampleList) {
        return new NaturalPredatorMIPSampleChartService().of(aSampleList, this.getNaturalPredatorTargetList());
    } 
    
    public List<DAEAndOccurrenceDTO> getDefoliationChart(List<MIPSample> aSampleList) {
        return new DefoliationChartService().of(aSampleList);
    }

    public List<Region> readRegionsAvailableByMacroRegionId(Long selectedMacroRegionId) {
        return this.mipSampleService.readAllRegionsFor(selectedMacroRegionId);
    }
    
    public List<MacroRegion> readAllMacroRegionsWithSurvey() {
        return this.mipSampleService.readAllMacroRegionsWithSurvey();
    }
    
    public List<City> readCitiesAvailableByRegionId(Long aRegionId) {
        try {
            return this.mipSampleService.readAllCitiesByRegionId(aRegionId);
            
        } catch (EntityNotFoundException ex) {
            return null;
        }
    }
    
    public List<Field> readURsAvailableByCityId(Long aCityId) {
        return this.mipSampleService.readAllFieldsByCityId(aCityId);
    }
    
    public List<MIPSample> readSamples() {
        return mipSampleService.readAll();
    }
    
    public List<MIPSample> readMIPSamplesByMacroRegionId(Long aMacroRegionId) {
        return mipSampleService.readByMacroRegionId(aMacroRegionId);
    }
    
    public List<MIPSample> readMIPSamplesByRegionId(Long aRegionId) {
        return mipSampleService.readByRegionId(aRegionId);
    }
    
    public List<MIPSample> readMIPSamplesByCityId(Long aCityId) {
        return mipSampleService.readByCityId(aCityId);
    }
    
    public List<MIPSample> readMIPSamplesByURId(Long anURId) {
        return mipSampleService.readByURId(anURId);
    }
    
    public List<MIPSample> readMIPSamplesByMIPUser(MIPUser aMIPUser) {
        return mipSampleService.readByMIPUser(aMIPUser);
    }
    
    public Optional<MacroRegion> readMacroRegionById(Long aMacroRegionId) {
        return mipSampleService.readyByMacroRegionId(aMacroRegionId);
    }
    
    public Optional<Region> readRegionById(Long aRegionId) {
        return mipSampleService.readyRegionById(aRegionId);
    }
    
    public Optional<City> readCityById(Long aCityId) {
        return mipSampleService.readyCityById(aCityId);
    }
    
    public Optional<Field> readFieldById(Long aFieldId) {
        return mipSampleService.readFieldById(aFieldId);
    }
    
    private List<Pest> getCaterpillarTargetList() {
        
        return List.of(
                this.mipSampleService.readPestById(1L).get(),
                this.mipSampleService.readPestById(2L).get(),
                this.mipSampleService.readPestById(3L).get(),
                this.mipSampleService.readPestById(4L).get(),
                this.mipSampleService.readPestById(5L).get(),
                this.mipSampleService.readPestById(6L).get(),
                this.mipSampleService.readPestById(7L).get(),
                this.mipSampleService.readPestById(8L).get());
    }
    
    private List<Pest> getBedBugTargetList() {
        return List.of(
                this.mipSampleService.readPestById(9L).get(),
                this.mipSampleService.readPestById(10L).get(),
                this.mipSampleService.readPestById(11L).get(),
                this.mipSampleService.readPestById(12L).get(),
                this.mipSampleService.readPestById(13L).get(),
                this.mipSampleService.readPestById(14L).get(),
                this.mipSampleService.readPestById(15L).get(),
                this.mipSampleService.readPestById(16L).get(),
                this.mipSampleService.readPestById(17L).get(),
                this.mipSampleService.readPestById(18L).get());
    }
    
    private List<PestNaturalPredator> getNaturalPredatorTargetList() {
        return Stream.of(
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
                this.mipSampleService.readPredatorById(12L).get())
                .collect(Collectors.toList());
    }

    public List<MIPSample> readMIPSamplesBySurveyId(Long aSurveyId) {
        return mipSampleService.readBySurveyId(aSurveyId);
    }

    public Survey readSurveyById(Long aSurveyId) throws EntityNotFoundException {
        return surveyService.readById(aSurveyId);
    }
}
