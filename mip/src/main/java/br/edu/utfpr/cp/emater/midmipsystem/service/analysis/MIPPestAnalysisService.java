package br.edu.utfpr.cp.emater.midmipsystem.service.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSamplePestOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.PestService;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.HarvestService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
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

    private final PestService pestService;
    private final HarvestService harvestService;
    private final MIPSampleService mipSampleService;

    public LineChartModel createCaterpillarFluctuationChart() throws EntityNotFoundException {
        
        var pests = pestService.readByScientificNameStartsWith("Anticarsia").orElse(new ArrayList<Pest>());
        System.out.println(pests.size());
        var theOccurrences = mipSampleService.readById(163L).getDAEAndPestOccurrenceByPestSet(new HashSet<>(pests));
        
        theOccurrences.get().forEach((i, d) -> System.out.println ("DAE: " + i + ", Occ: " + d));
        

        var result = new LineChartModel();

        var pestNames = List.of("Anticarsia", "Diabrotica");
        var pestNameAndPestsMap = new HashMap<String, List<Pest>>();

        pestNames.forEach(currentPestName
                -> pestNameAndPestsMap
                        .put(currentPestName,
                                pestService.readByScientificNameStartsWith(currentPestName)
                                        .orElse(new ArrayList<Pest>()))
        );

        var currentHarvest = harvestService.readAll().get(0);

        var allMIPSamplesForCurrentHarvest = mipSampleService.readAll().stream()
                .filter(sample -> sample.getSurvey().getHarvest().equals(currentHarvest))
                .collect(Collectors.toList());

        pestNameAndPestsMap.keySet().forEach(currentPestName
                -> {
            result.addSeries(getSerie(allMIPSamplesForCurrentHarvest, pestNameAndPestsMap.get(currentPestName)));
        });

        var daes = new HashSet<Integer>();
        var occurrences = new HashSet<Double>();

        result.getSeries().forEach(serie -> {
            serie.getData().forEach((key, value) -> {
                daes.add((int) key);
                occurrences.add((double) value);
            });
        });

        setChartInfo(result, daes, occurrences);

        return result;
    }

    private void setChartInfo(LineChartModel lineChartModel, Set<Integer> daes, Set<Double> occurrences) {
        lineChartModel.setTitle("Flutuação de Lagartas");
        lineChartModel.setLegendPosition("e");
        lineChartModel.setShowPointLabels(true);

        Axis xAxis = lineChartModel.getAxis(AxisType.X);
        xAxis.setLabel("Dias Após Emergência");
        xAxis.setTickInterval("5");
        xAxis.setMin(0);
        xAxis.setMax(Collections.max(daes) + 5);

        Axis yAxis = lineChartModel.getAxis(AxisType.Y);
        yAxis.setLabel("No. Insetos/metro");
        yAxis.setTickInterval("1");
        yAxis.setMin(0);
        yAxis.setMax(Collections.max(occurrences) + 1);
    }

    private LineChartSeries getSerie(List<MIPSample> allMIPSamplesForCurrentHarvest, List<Pest> pest1) {

        var result = new LineChartSeries();

        var daeOccurrenceCountValues = this.getDAEOccurrenceCountValues(allMIPSamplesForCurrentHarvest, pest1);

        pest1.forEach(e -> {
            result.setLabel(e.getScientificName());

            daeOccurrenceCountValues.keySet().forEach(s -> result.set(s, daeOccurrenceCountValues.get(s)));

        });

        return result;
    }

    private Map<Integer, Double> getDAEOccurrenceCountValues(List<MIPSample> allMIPSamplesForCurrentHarvest, List<Pest> pest1) {

        var result = new HashMap<Integer, Double>();

        allMIPSamplesForCurrentHarvest.stream().forEach(currentSample -> {

            var emergence = this.getEmergenceDate(currentSample);
            var sample = this.getSampleDate(currentSample);

            int dae = 0;

            if (emergence.isPresent()) {
                if (sample.isPresent()) {
                    dae = this.calculateDaysAfterEmergence(emergence.get(), sample.get());
                }
            }

            double occurrenceCount = 0;
            var resultOfContPest = countPest(currentSample, pest1);

            if (resultOfContPest.isPresent()) {
                occurrenceCount = resultOfContPest.get();
            }

            result.put(dae, occurrenceCount);

        });

        return result;
    }

    private Optional<Double> countPest(MIPSample aMIPSample, List<Pest> targetPests) {
        if (aMIPSample.getMipSamplePestOccurrence() == null) {
            return Optional.empty();
        } else {
            var pestOccurrences = getPestOccurrencesForSpecificPest(aMIPSample.getMipSamplePestOccurrence(), targetPests);

            if (pestOccurrences.isPresent()) {
                return Optional.of(pestOccurrences.get().stream().mapToDouble(MIPSamplePestOccurrence::getValue).sum());
            } else {
                return Optional.empty();
            }
        }

    }

    private Optional<List<MIPSamplePestOccurrence>> getPestOccurrencesForSpecificPest(Set<MIPSamplePestOccurrence> pestOccurrences, List<Pest> targetPests) {

        var result = new ArrayList<MIPSamplePestOccurrence>();

        for (MIPSamplePestOccurrence currentOccurrence : pestOccurrences) {
            for (Pest pestTarget : targetPests) {
                if (currentOccurrence.getPest().equals(pestTarget)) {
                    result.add(currentOccurrence);
                }
            }
        }

        if (result.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(result);
        }

    }

    private Optional<Date> getEmergenceDate(MIPSample aMIPSample) {
        if (aMIPSample.getSurvey() == null) {
            return Optional.empty();

        } else {
            if (aMIPSample.getSurvey().getEmergenceDate() != null) {
                return Optional.of(aMIPSample.getSurvey().getEmergenceDate());

            } else {
                return Optional.empty();
            }
        }
    }

    private Optional<Date> getSampleDate(MIPSample aMIPSample) {
        if (aMIPSample.getSampleDate() != null) {
            return Optional.of(aMIPSample.getSampleDate());
        } else {
            return Optional.empty();
        }
    }

    private int calculateDaysAfterEmergence(Date emergence, Date sample) {

        long diffInMillies = (sample.getTime() - emergence.getTime());

        if (diffInMillies > 0) {
            var result = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            return (int) (result + 1);

        } else {
            return 0;
        }
    }

}
