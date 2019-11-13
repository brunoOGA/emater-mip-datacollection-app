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

    public LineChartModel getPestFluctuationChart() {

        var targetPests = this.getPests();

        List<MIPSample> targetMIPSamples = this.getSamples();

        var result = this.getLinearChartModel(targetPests, targetMIPSamples);

        setChartInfo(result, null, null);

        return result;
    }

    private LineChartModel getLinearChartModel(List<Pest> targetPests, List<MIPSample> targetMIPSamples) {

        var result = new LineChartModel();

        for (Pest currentTargetPest : targetPests) {

            var mapFound = new HashMap<Integer, Double>();

            for (MIPSample currentSample : targetMIPSamples) {
                var sampleFound = currentSample.getDAEAndPestOccurrenceByPest(currentTargetPest);

                if (sampleFound.isPresent()) {
                    mapFound.putAll(sampleFound.get());
                }
            }

            result.addSeries(this.getSerie(currentTargetPest, mapFound));
        }

        return result;
    }

    private List<MIPSample> getSamples() {
        return mipSampleService.readAll()
                .stream()
                .filter(sample -> sample.getHarvestId().isPresent())
                .filter(sample -> sample.getHarvestId().get().equals(1L))
                .collect(Collectors.toList());
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

    public LineChartModel getPestFluctuationChartForUR(Long selectedURId) {

        List<MIPSample> samples = this.getSamples().stream()
                .filter(currentSample -> currentSample.getFieldId().isPresent())
                .filter(currentSample -> currentSample.getFieldId().get().equals(selectedURId))
                .collect(Collectors.toList());

        List<MIPSample> targetMIPSamples = null;

        if (samples == null || samples.size() == 0) {
            targetMIPSamples = this.getSamples();
        } else {
            targetMIPSamples = samples;
        }

        var targetPests = this.getPests();

        var result = this.getLinearChartModel(targetPests, targetMIPSamples);

        setChartInfo(result, null, null);

        return result;
    }

    public LineChartModel getPestFluctuationChartForMacroRegion(Long aMacroRegionId) {

        var citiesInTheMacroRegion = new ArrayList<City>();

        for (Region currentRegion : mipSampleService.readAllRegionsFor(aMacroRegionId)) {
            for (City currentCity : currentRegion.getCities()) {
                citiesInTheMacroRegion.add(currentCity);
            }
        }

        List<MIPSample> samples = new ArrayList<>();
        for (City currentCity : citiesInTheMacroRegion) {
            samples.addAll(this.getSamples().stream()
                    .filter(currentSample -> currentSample.getCity().isPresent())
                    .filter(currentSample -> currentSample.getCity().get().equals(currentCity))
                    .collect(Collectors.toList())
            );
        }

        List<MIPSample> targetMIPSamples = null;

        if (samples == null || samples.size() == 0) {
            targetMIPSamples = this.getSamples();
        } else {
            targetMIPSamples = samples;
        }

        var targetPests = this.getPests();

        var result = this.getLinearChartModel(targetPests, targetMIPSamples);

        setChartInfo(result, null, null);

        return result;
    }

    public LineChartModel getPestFluctuationChartForRegion(Long aRegionId) throws EntityNotFoundException {

        var citiesInTheRegion = mipSampleService.readAllCitiesByRegionId(aRegionId);

        List<MIPSample> samples = new ArrayList<>();

        for (City currentCity : citiesInTheRegion) {
            samples.addAll(this.getSamples().stream()
                    .filter(currentSample -> currentSample.getCity().isPresent())
                    .filter(currentSample -> currentSample.getCity().get().equals(currentCity))
                    .collect(Collectors.toList())
            );
        }

        List<MIPSample> targetMIPSamples = null;

        if (samples == null || samples.size() == 0) {
            targetMIPSamples = this.getSamples();
            
        } else {
            targetMIPSamples = samples;
        }

        var targetPests = this.getPests();

        var result = this.getLinearChartModel(targetPests, targetMIPSamples);

        setChartInfo(result, null, null);

        return result;
    }

    public LineChartModel getPestFluctuationChartForCity(Long aCityId) {

        List<MIPSample> samples = new ArrayList<>();

        this.getSamples().stream()
                .filter(currentSample -> currentSample.getCity().isPresent())
                .filter(currentSample -> currentSample.getCity().get().getId().equals(aCityId))
                .collect(Collectors.toList());

        List<MIPSample> targetMIPSamples = null;

        if (samples == null || samples.size() == 0) {
            targetMIPSamples = this.getSamples();
            
        } else {
            targetMIPSamples = samples;
        }

        var targetPests = this.getPests();

        var result = this.getLinearChartModel(targetPests, targetMIPSamples);

        setChartInfo(result, null, null);

        return result;
    }
}
