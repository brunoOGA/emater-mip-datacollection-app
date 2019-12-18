package br.edu.utfpr.cp.emater.midmipsystem.service.analysis.chart;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestNaturalPredator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NaturalPredatorMIPSampleChartService extends AbstractMIPSampleChartService<PestNaturalPredator> {

    @Override
    protected Map<PestNaturalPredator, List<DAEAndOccurrenceDTO>> retrieveData(List<MIPSample> aSampleList, List<PestNaturalPredator> aTargetList) {
        var result = new HashMap<PestNaturalPredator, List<DAEAndOccurrenceDTO>>();

        aTargetList.forEach(currentPredator
                -> result.put(currentPredator,
                        aSampleList.stream()
                                .filter(currentSample -> currentSample.getDAEAndPredatorOccurrenceByPredator(currentPredator).isPresent())
                                .map(currentSample -> currentSample.getDAEAndPredatorOccurrenceByPredator(currentPredator).get())
                                .collect(Collectors.toList())
                )
        );

        return result;

    }

    @Override
    protected Map<String, List<DAEAndOccurrenceDTO>> format(Map<PestNaturalPredator, Map<Integer, Double>> input) {
        var result = new HashMap<String, List<DAEAndOccurrenceDTO>>();

        int greatestDAE = 0;

        for (PestNaturalPredator currentTarget : input.keySet()) {

            var currentMap = input.get(currentTarget);

            for (Integer currentDAE : currentMap.keySet()) {
                if (currentDAE > greatestDAE) {
                    greatestDAE = currentDAE;
                }
            }
        }

        final int maximumDAE = (int) greatestDAE + 3;

        input.forEach((target, map) -> {

            var dataset = new ArrayList<DAEAndOccurrenceDTO>();

            for (int i = 0; i < maximumDAE; i++) {
                dataset.add(DAEAndOccurrenceDTO.builder().dae(i).occurrence(map.getOrDefault(i, 0.0)).build());
            }

            Comparator<DAEAndOccurrenceDTO> daeAndOccurrencesComparator = Comparator.comparingInt(DAEAndOccurrenceDTO::getDae);

            result.put(target.getDescription(), dataset.stream().sorted(daeAndOccurrencesComparator).collect(Collectors.toList()));
        });

        return result;
    }
}
