package br.edu.utfpr.cp.emater.midmipsystem.service.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@RequiredArgsConstructor
public abstract class AbstractMIPSampleAnalysis {

    @Getter(AccessLevel.PROTECTED)
    private final MIPSampleService mipSampleService;

    public abstract LineChartModel getChart();

    public abstract LineChartModel getChart(List<MIPSample> MIPSampleData);

    LineChartSeries getSerie(Pest aPest, Map<Integer, Double> aMappingDAEOccurrence
    ) {

        var result = new LineChartSeries();

        result.setLabel(String.format("%s (%s)", aPest.getScientificName().length() == 0 ? aPest.getUsualName() : aPest.getScientificName(), aPest.getPestSize().getName()));

        aMappingDAEOccurrence.keySet().forEach(item -> {
            result.set(item, aMappingDAEOccurrence.get(item));
        });

        return result;
    }

    public List<Region> getRegionsAvailableFor(Long selectedMacroRegionId) {
        return this.mipSampleService.readAllRegionsFor(selectedMacroRegionId);
    }

    public List<MacroRegion> readAllMacroRegionsWithSurvey() {
        return this.mipSampleService.readAllMacroRegionsWithSurvey();
    }

    public List<City> getCitiesAvailableFor(Long aRegionId) {
        try {
            return this.mipSampleService.readAllCitiesByRegionId(aRegionId);

        } catch (EntityNotFoundException ex) {
            return null;
        }
    }

    public List<Field> getURsAvailableFor(Long aCityId) {
        return this.mipSampleService.readAllFieldsByCityId(aCityId);
    }

    public List<MIPSample> getSamples() {
        return mipSampleService.readAll();
    }

    public List<MIPSample> getMIPSamplesByMacroRegionId(Long aMacroRegionId) {
        return mipSampleService.readByMacroRegionId(aMacroRegionId);
    }

    public List<MIPSample> getMIPSamplesByRegionId(Long aRegionId) {
        return mipSampleService.readByRegionId(aRegionId);
    }

    public List<MIPSample> getMIPSamplesByCityId(Long aCityId) {
        return mipSampleService.readByCityId(aCityId);
    }

    public List<MIPSample> getMIPSamplesByURId(Long anURId) {
        return mipSampleService.readByURId(anURId);
    }
}