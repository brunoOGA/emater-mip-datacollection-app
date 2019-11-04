package br.edu.utfpr.cp.emater.midmipsystem.view.charts;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSamplePestOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.PestService;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.HarvestService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@RequiredArgsConstructor
public class PestMonitoring {

    private final PestService pestService;
    private final HarvestService harvestService;
    private final MIPSampleService mipSampleService;

    public LineChartModel createCaterpillarFluctuationChart() throws EntityNotFoundException {
        var result = new LineChartModel();

        result.setTitle("Flutuação de Lagartas");
        result.setLegendPosition("e");
        result.setShowPointLabels(true);

        Axis xAxis = result.getAxis(AxisType.X);
        xAxis.setLabel("Dias Após Emergência");
        xAxis.setTickInterval("5");
        xAxis.setMin(0);
        xAxis.setMax(30);

        Axis yAxis = result.getAxis(AxisType.Y);
        yAxis.setLabel("No. Insetos/metro");
        yAxis.setTickInterval("1");
        yAxis.setMin(0);
        yAxis.setMax(5);

        var pest1 = pestService.readByScientificNameStartsWith("Anticarsia").orElseThrow();
        var pest2 = pestService.readByScientificNameStartsWith("Diabrotica").orElseThrow();

        var currentHarvest = harvestService.readAll().get(0);

        var allMIPSamplesForCurrentHarvest = mipSampleService.readAll().stream()
                .filter(sample -> sample.getSurvey().getHarvest().equals(currentHarvest))
                .collect(Collectors.toList());
        
        result.addSeries(getSerie(allMIPSamplesForCurrentHarvest, pest1));
        result.addSeries(getSerie(allMIPSamplesForCurrentHarvest, pest2));
        
        return result;

    }

    private LineChartSeries getSerie(List<MIPSample> allMIPSamplesForCurrentHarvest, List<Pest> pest1) {

        var result = new LineChartSeries();

        var seriesValues = new HashMap<Integer, Double>();

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

            seriesValues.put(dae, occurrenceCount);

        });
        
        pest1.forEach(e -> {
            result.setLabel(e.getScientificName());

            seriesValues.keySet().forEach(s -> result.set(s, seriesValues.get(s)));

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
