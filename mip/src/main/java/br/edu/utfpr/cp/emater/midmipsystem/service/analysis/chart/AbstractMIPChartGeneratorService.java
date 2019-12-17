package br.edu.utfpr.cp.emater.midmipsystem.service.analysis.chart;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.DAEAndOccurrenceDTO;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractMIPChartGeneratorService<T> {

    protected BiFunction<List<MIPSample>, List<T>, Map<T, List<DAEAndOccurrenceDTO>>> retrieveDataFunction;
    protected Function<Map<T, List<DAEAndOccurrenceDTO>>, Map<T, Map<Integer, Double>>> filterFunction;
    protected Function<Map<T, Map<Integer, Double>>, Map<String, List<DAEAndOccurrenceDTO>>> formatFunction;

    public Map<String, List<DAEAndOccurrenceDTO>> of(List<MIPSample> aSampleList, List<T> aTargetList) {

        return formatFunction.apply(
                filterFunction.apply(
                        retrieveDataFunction.apply(aSampleList, aTargetList)
                )
        );
    }
    
    public AbstractMIPChartGeneratorService() {
        this.filterFunction = (occurrences) -> {
            var result = new HashMap<T, Map<Integer, Double>>();

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
        };
        
        this.formatFunction = (input) -> {
            
            var result = new HashMap<String, List<DAEAndOccurrenceDTO>>();

            int greatestDAE = 0;

            for (T currentTarget : input.keySet()) {

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

                result.put(((Pest)target).getDescription(), dataset.stream().sorted(daeAndOccurrencesComparator).collect(Collectors.toList()));
            });

            return result;
        };
    }
}
