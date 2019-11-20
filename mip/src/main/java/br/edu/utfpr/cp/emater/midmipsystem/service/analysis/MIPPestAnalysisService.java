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
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MIPPestAnalysisService {

    private final MIPSampleService mipSampleService;

    public LineChartModel pestOccurrenceLineChart() {
        var samples = this.getSamples();
        var pests = this.getPests();
        var pestAndDAEAndOccurrences = getDAEAndOccurrences(pests, samples);
        var chartSeries = getChartSeries(pestAndDAEAndOccurrences);

        var chartModel = new LineChartModel();
        chartSeries.forEach(chartModel::addSeries);
        this.setLineChartInfo(chartModel, pestAndDAEAndOccurrences);

        return chartModel;
    }

    private void setLineChartInfo(LineChartModel aChartModel, Map<Pest, List<DAEAndOccurrence>> pestsDAEsAndOccurrences) {
        aChartModel.setLegendPosition("e");
        aChartModel.setShowPointLabels(true);

        Axis xAxis = aChartModel.getAxis(AxisType.X);
        xAxis.setLabel("Dias Após Emergência");
        xAxis.setTickInterval("10");
        xAxis.setMin(0);
        xAxis.setMax(60);

        Axis yAxis = aChartModel.getAxis(AxisType.Y);
        yAxis.setLabel("No. Insetos/metro");
        yAxis.setTickInterval("0.5");
        yAxis.setMin(0);
        yAxis.setMax(5);
        yAxis.setTickFormat("%#.2f");
        yAxis.setMax(3);

    }

    private List<LineChartSeries> getChartSeries(Map<Pest, List<DAEAndOccurrence>> occurrencesGrouppedByPest) {

        var result = new ArrayList<LineChartSeries>();

        occurrencesGrouppedByPest.keySet().stream().forEach(currentPest -> {
            
            var currentSerie = new LineChartSeries(currentPest.getDescription());

            occurrencesGrouppedByPest.get(currentPest).forEach(currentDAEAndOccurrence -> {
                currentSerie.set(currentDAEAndOccurrence.getDae(), currentDAEAndOccurrence.getOccurrence());
            });

            result.add(currentSerie);

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

    private List<Pest> getPests() {
        return List.of(
                mipSampleService.readPestById(1L).get(),
                mipSampleService.readPestById(2L).get(),
                mipSampleService.readPestById(3L).get(),
                mipSampleService.readPestById(4L).get(),
                mipSampleService.readPestById(5L).get(),
                mipSampleService.readPestById(6L).get(),
                mipSampleService.readPestById(7L).get(),
                mipSampleService.readPestById(8L).get());
    }

    private void setChartInfo(LineChartModel lineChartModel, Set<Integer> daes, Set<Double> occurrences) {
//        lineChartModel.setTitle("Flutuação de Lagartas");
        lineChartModel.setLegendPosition("e");
        lineChartModel.setShowPointLabels(true);

        Axis xAxis = lineChartModel.getAxis(AxisType.X);
        xAxis.setLabel("Dias Após Emergência");
        xAxis.setTickInterval("5");
        xAxis.setMin(0);
//        xAxis.setMax(Collections.max(daes) + 5);
        xAxis.setMax(40);

        Axis yAxis = lineChartModel.getAxis(AxisType.Y);
        yAxis.setLabel("No. Insetos/metro");
        yAxis.setTickInterval("0.5");
        yAxis.setMin(0);
//        yAxis.setMax(Collections.max(occurrences) + 1);
        yAxis.setTickFormat("%#.2f");
        yAxis.setMax(3);
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
