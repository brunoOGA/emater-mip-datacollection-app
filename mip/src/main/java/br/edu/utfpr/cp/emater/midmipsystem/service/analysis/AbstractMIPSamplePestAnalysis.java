package br.edu.utfpr.cp.emater.midmipsystem.service.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.jdt.core.compiler.CharOperation;

public abstract class AbstractMIPSamplePestAnalysis extends AbstractMIPSampleAnalysis {

    public AbstractMIPSamplePestAnalysis(MIPSampleService mipSampleService) {
        super(mipSampleService);
    }

    public Map<String, List<DAEAndOccurrenceDTO>> getChart(List<MIPSample> MIPSampleData) {

        var pests = this.getPests();

        var pestAndDAEAndOccurrences = getDAEAndOccurrences(pests, MIPSampleData);

        var pestAndDAEAndOccurrencesMap = consolidateDAEAndOccurrences(pestAndDAEAndOccurrences);

        return this.convertIntoChartFormat(pestAndDAEAndOccurrencesMap);
    }

    public Map<String, List<DAEAndOccurrenceDTO>> getChart() {

        var samples = this.readSamples();

        var pests = this.getPests();

        var pestAndDAEAndOccurrences = getDAEAndOccurrences(pests, samples);

        var pestAndDAEAndOccurrencesMap = consolidateDAEAndOccurrences(pestAndDAEAndOccurrences);

        return this.convertIntoChartFormat(pestAndDAEAndOccurrencesMap);
    }

    protected abstract List<Pest> getPests();

    Map<Pest, List<DAEAndOccurrenceDTO>> getDAEAndOccurrences(List<Pest> pests, List<MIPSample> samples) {

        var result = new HashMap<Pest, List<DAEAndOccurrenceDTO>>();

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

    Map<Pest, Map<Integer, Double>> consolidateDAEAndOccurrences(Map<Pest, List<DAEAndOccurrenceDTO>> occurrences) {

        var result = new HashMap<Pest, Map<Integer, Double>>();

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

    public Map<String, List<DAEAndOccurrenceDTO>> convertIntoChartFormat(Map<Pest, Map<Integer, Double>> input) {

        var result = new HashMap<String, List<DAEAndOccurrenceDTO>>();

        int greatestDAE = 0;

        for (Pest currentPest : input.keySet()) {

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
