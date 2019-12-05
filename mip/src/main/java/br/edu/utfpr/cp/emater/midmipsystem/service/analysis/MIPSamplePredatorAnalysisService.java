package br.edu.utfpr.cp.emater.midmipsystem.service.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestNaturalPredator;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MIPSamplePredatorAnalysisService extends AbstractMIPSampleAnalysis {

    public MIPSamplePredatorAnalysisService(MIPSampleService mipSampleService) {
        super(mipSampleService);
    }

    public Map<String, List<DAEAndOccurrenceDTO>> getChart(List<MIPSample> MIPSampleData) {

        var predators = this.getPredators();

        var pestAndDAEAndOccurrences = getDAEAndOccurrences(predators, MIPSampleData);

        var pestAndDAEAndOccurrencesMap = consolidateDAEAndOccurrences(pestAndDAEAndOccurrences);

        return this.convertPredatorIntoChartFormat(pestAndDAEAndOccurrencesMap);
    }

    public Map<String, List<DAEAndOccurrenceDTO>> getChart() {
        var samples = this.readSamples();

        var predators = this.getPredators();

        var pestAndDAEAndOccurrences = getDAEAndOccurrences(predators, samples);

        var pestAndDAEAndOccurrencesMap = consolidateDAEAndOccurrences(pestAndDAEAndOccurrences);

        return this.convertPredatorIntoChartFormat(pestAndDAEAndOccurrencesMap);
    }

    Map<PestNaturalPredator, List<DAEAndOccurrenceDTO>> getDAEAndOccurrences(List<PestNaturalPredator> predators, List<MIPSample> samples) {

        var result = new HashMap<PestNaturalPredator, List<DAEAndOccurrenceDTO>>();

        predators.forEach(currentPredator
                -> result.put(currentPredator,
                        samples.stream()
                                .filter(currentSample -> currentSample.getDAEAndPredatorOccurrenceByPredator(currentPredator).isPresent())
                                .map(currentSample -> currentSample.getDAEAndPredatorOccurrenceByPredator(currentPredator).get())
                                .collect(Collectors.toList())
                )
        );

        return result;
    }

    Map<PestNaturalPredator, Map<Integer, Double>> consolidateDAEAndOccurrences(Map<PestNaturalPredator, List<DAEAndOccurrenceDTO>> occurrences) {

        var result = new HashMap<PestNaturalPredator, Map<Integer, Double>>();

        occurrences.keySet().forEach(currentOccurrence -> {

            result.put(currentOccurrence,
                    occurrences.get(currentOccurrence).stream()
                            .collect(
                                    Collectors.groupingBy(
                                            DAEAndOccurrenceDTO::getDae,
                                            Collectors.averagingDouble(DAEAndOccurrenceDTO::getOccurrence)
                                    )
                            )
            );
        });

        return result;
    }

    protected List<PestNaturalPredator> getPredators() {
        return List.of(
                this.getMipSampleService().readPredatorById(1L).get(),
                this.getMipSampleService().readPredatorById(2L).get(),
                this.getMipSampleService().readPredatorById(3L).get(),
                this.getMipSampleService().readPredatorById(4L).get(),
                this.getMipSampleService().readPredatorById(5L).get(),
                this.getMipSampleService().readPredatorById(6L).get(),
                this.getMipSampleService().readPredatorById(7L).get(),
                this.getMipSampleService().readPredatorById(8L).get(),
                this.getMipSampleService().readPredatorById(9L).get(),
                this.getMipSampleService().readPredatorById(10L).get(),
                this.getMipSampleService().readPredatorById(11L).get(),
                this.getMipSampleService().readPredatorById(12L).get());

    }

    public Map<String, List<DAEAndOccurrenceDTO>> convertPredatorIntoChartFormat(Map<PestNaturalPredator, Map<Integer, Double>> input) {

        var result = new HashMap<String, List<DAEAndOccurrenceDTO>>();

        int greatestDAE = 0;

        for (PestNaturalPredator currentPest : input.keySet()) {

            var currentMap = input.get(currentPest);

            for (Integer currentDAE : currentMap.keySet()) {
                if (currentDAE > greatestDAE) {
                    greatestDAE = currentDAE;
                }
            }
        }

        final int maximumDAE = (int) greatestDAE + 3;

        input.forEach((pest, map) -> {

            var dataset = new ArrayList<DAEAndOccurrenceDTO>();

            for (int i = 0; i < maximumDAE; i++) {
                dataset.add(DAEAndOccurrenceDTO.builder().dae(i).occurrence(map.getOrDefault(i, 0.0)).build());
            }

            Comparator<DAEAndOccurrenceDTO> daeAndOccurrencesComparator = Comparator.comparingInt(DAEAndOccurrenceDTO::getDae);

            result.put(pest.getDescription(), dataset.stream().sorted(daeAndOccurrencesComparator).collect(Collectors.toList()));
        });

        return result;
    }

}
