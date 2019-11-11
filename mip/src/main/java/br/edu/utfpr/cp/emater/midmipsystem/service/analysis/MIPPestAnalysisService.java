package br.edu.utfpr.cp.emater.midmipsystem.service.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
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

    public LineChartModel createCaterpillarFluctuationChart() throws Exception {

        var targetPests = List.of(mipSampleService.readPestById(1L).get(), mipSampleService.readPestById(23L).get());
        var targetMIPSamples = mipSampleService.readAll()
                .stream()
                .filter(sample -> sample.getHarvestId().isPresent())
                .filter(sample -> sample.getHarvestId().get().equals(1L))
                .collect(Collectors.toList());

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

        setChartInfo(result, null, null);
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

        result.setLabel(String.format("%s (%s)", aPest.getScientificName(), aPest.getPestSize().getName()));

        aMappingDAEOccurrence.keySet().forEach(item -> {
            result.set(item, aMappingDAEOccurrence.get(item));
        });

        return result;
    }
}
