package br.edu.utfpr.cp.emater.midmipsystem.service.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.analysis.DAEAndOccurrence;
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
public abstract class AbstractMIPPestAnalysis {

    @Getter (AccessLevel.PROTECTED)
    private final MIPSampleService mipSampleService;

    public LineChartModel getPestLineChart() {
        var samples = this.getSamples();

        var pests = this.getPests();

        var pestAndDAEAndOccurrences = getDAEAndOccurrences(pests, samples);

        var pestAndDAEAndOccurrencesMap = consolidateDAEAndOccurrences(pestAndDAEAndOccurrences);

        var chartSeries = getChartSeries(pestAndDAEAndOccurrencesMap);

        var chartModel = new LineChartModel();
        chartSeries.forEach(chartModel::addSeries);
        this.setLineChartInfo(chartModel);

        return chartModel;
    }

    protected abstract List<Pest> getPests();
    
    private void setLineChartInfo(LineChartModel aChartModel) {
        
        aChartModel.setLegendPosition("nw");
        
        aChartModel.setShowPointLabels(true);
        aChartModel.setZoom(true);
        aChartModel.setAnimate(true);

        Axis xAxis = aChartModel.getAxis(AxisType.X);
        xAxis.setLabel("Dias Após Emergência");

        Axis yAxis = aChartModel.getAxis(AxisType.Y);
        yAxis.setLabel("No. Insetos/metro");
        yAxis.setTickFormat("%#.2f");
    }

    private List<LineChartSeries> getChartSeries(Map<Pest, Map<Integer, Double>> occurrencesGrouppedByPest) {

        var result = new ArrayList<LineChartSeries>();

        occurrencesGrouppedByPest.keySet().stream().forEach(currentPest -> {

            var currentSerie = new LineChartSeries(currentPest.getDescription());

            var currentPestOccurrence = occurrencesGrouppedByPest.get(currentPest);

            currentPestOccurrence.keySet().forEach(currentDAE -> {
                currentSerie.set(currentDAE, currentPestOccurrence.get(currentDAE));
            });

            result.add(currentSerie);

        });

        return result;
    }

    private Map<Pest, Map<Integer, Double>> consolidateDAEAndOccurrences(Map<Pest, List<DAEAndOccurrence>> occurrences) {

        var result = new HashMap<Pest, Map<Integer, Double>>();

        occurrences.keySet().forEach(currentOccurrence -> {

            result.put(currentOccurrence,
                    occurrences.get(currentOccurrence).stream()
                            .collect(
                                    Collectors.groupingBy(
                                            DAEAndOccurrence::getDae,
                                            Collectors.averagingDouble(DAEAndOccurrence::getOccurrence)
                                    )
                            )
            );
        });

        return result;
    }

    private Map<Pest, List<DAEAndOccurrence>> getDAEAndOccurrences(List<Pest> pests, List<MIPSample> samples) {

        var result = new HashMap<Pest, List<DAEAndOccurrence>>();

        pests.forEach(currentPest
                -> result.put(currentPest,
                        samples.stream()
                                .filter(currentSample -> currentSample.getDAEAndPestOccurrenceByPest(currentPest).isPresent())
                                .map(currentSample -> currentSample.getDAEAndPestOccurrenceByPest(currentPest).get())
                                .collect(Collectors.toList())
                )
        );

        return result;
    }

    private List<MIPSample> getSamples() {
        return mipSampleService.readAll();
    }


    private LineChartSeries getSerie(Pest aPest, Map<Integer, Double> aMappingDAEOccurrence) {

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

    public List<MacroRegion> readAllMacroRegions() {
        return this.mipSampleService.readAllMacroRegions();
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
}
