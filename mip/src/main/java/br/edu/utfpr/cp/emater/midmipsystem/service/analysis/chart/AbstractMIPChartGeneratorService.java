package br.edu.utfpr.cp.emater.midmipsystem.service.analysis.chart;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.DAEAndOccurrenceDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractMIPChartGeneratorService<T> {

    public Map<String, List<DAEAndOccurrenceDTO>> of(List<MIPSample> aSampleList, List<T> aTargetList) {

        var dataRetrieved = this.retrieveData(aSampleList, aTargetList);
        var filteredData = this.filter(dataRetrieved);
        var formattedData = this.format(filteredData);

        return formattedData;
    }

    protected abstract Map<T, List<DAEAndOccurrenceDTO>> retrieveData(List<MIPSample> aSampleList, List<T> aTargetList);

    protected Map<T, Map<Integer, Double>> filter(Map<T, List<DAEAndOccurrenceDTO>> anOccurrenceList) {

        var result = new HashMap<T, Map<Integer, Double>>();

        anOccurrenceList.keySet().forEach(currentOccurrence -> {

            result.put(currentOccurrence,
                    anOccurrenceList.get(currentOccurrence).stream()
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

    protected abstract Map<String, List<DAEAndOccurrenceDTO>> format(Map<T, Map<Integer, Double>> input);
}
