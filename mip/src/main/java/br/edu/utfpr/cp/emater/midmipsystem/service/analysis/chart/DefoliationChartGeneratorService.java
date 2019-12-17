package br.edu.utfpr.cp.emater.midmipsystem.service.analysis.chart;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.DAEAndOccurrenceDTO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefoliationChartGeneratorService {

    public List<DAEAndOccurrenceDTO> of(List<MIPSample> aSampleList) {

        var dataRetrieved = this.retrieveData(aSampleList);
        var filteredData = this.filter(dataRetrieved);
        var formattedData = this.format(filteredData);

        return formattedData;
    }

    protected List<DAEAndOccurrenceDTO> retrieveData(List<MIPSample> samples) {
        var result = samples.stream()
                .map(currentSample -> DAEAndOccurrenceDTO.builder().dae(currentSample.getDAE()).occurrence(currentSample.getDefoliation()).build())
                .collect(Collectors.toList());

        return result;
    }

    protected Map<Integer, Double> filter(List<DAEAndOccurrenceDTO> occurrences) {

        Comparator<DAEAndOccurrenceDTO> daeAndOccurrencesComparator = Comparator.comparingInt(DAEAndOccurrenceDTO::getDae);

        var result = occurrences.stream()
                .sorted(daeAndOccurrencesComparator)
                .collect(
                        Collectors.groupingBy(
                                DAEAndOccurrenceDTO::getDae,
                                Collectors.averagingDouble(DAEAndOccurrenceDTO::getOccurrence)
                        )
                );

        return result;
    }

    protected List<DAEAndOccurrenceDTO> format(Map<Integer, Double> input) {
        
        var result = new ArrayList<DAEAndOccurrenceDTO>();

        input.forEach((dae, occurrence) -> {
            result.add(DAEAndOccurrenceDTO.builder().dae(dae).occurrence(occurrence).build());
        });

        Comparator<DAEAndOccurrenceDTO> daeAndOccurrencesComparator = Comparator.comparingInt(DAEAndOccurrenceDTO::getDae);

        return result.stream().sorted(daeAndOccurrencesComparator).collect(Collectors.toList());
    }

}
