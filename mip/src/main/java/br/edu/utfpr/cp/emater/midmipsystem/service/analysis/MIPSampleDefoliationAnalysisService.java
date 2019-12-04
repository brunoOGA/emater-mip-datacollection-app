package br.edu.utfpr.cp.emater.midmipsystem.service.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
;
import org.springframework.stereotype.Service;



@Service
public class MIPSampleDefoliationAnalysisService extends AbstractMIPSampleAnalysis {

    public MIPSampleDefoliationAnalysisService(MIPSampleService mipSampleService) {
        super(mipSampleService);
    }

    public List<DAEAndOccurrenceDTO> getChart() {
        var samples = this.readSamples();

        // To collect DAEAndDefoliation
        var DAEAndDefoliation = this.getDAEAndDefoliation(samples);

        // To generate map consolidating duplicated entries
        var DAEAndOccurrencesMap = consolidateDAEAndOccurrences(DAEAndDefoliation);

        return this.convertDefoliationIntoChartFormat(DAEAndOccurrencesMap);
    }

    public List<DAEAndOccurrenceDTO> getChart(List<MIPSample> MIPSampleData) {

        var DAEAndDefoliation = this.getDAEAndDefoliation(MIPSampleData);

        var DAEAndOccurrencesMap = consolidateDAEAndOccurrences(DAEAndDefoliation);

        return this.convertDefoliationIntoChartFormat(DAEAndOccurrencesMap);
    }

    List<DAEAndOccurrenceDTO> getDAEAndDefoliation(List<MIPSample> samples) {

        var result = samples.stream()
                .map(currentSample -> DAEAndOccurrenceDTO.builder().dae(currentSample.getDAE()).occurrence(currentSample.getDefoliation()).build())
                .collect(Collectors.toList());

        return result;
    }

    Map<Integer, Double> consolidateDAEAndOccurrences(List<DAEAndOccurrenceDTO> occurrences) {

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

    public List<DAEAndOccurrenceDTO> convertDefoliationIntoChartFormat(Map<Integer, Double> map) {

        var result = new ArrayList<DAEAndOccurrenceDTO>();

        map.forEach((dae, occurrence) -> {
            result.add(DAEAndOccurrenceDTO.builder().dae(dae).occurrence(occurrence).build());
        });
        
        Comparator<DAEAndOccurrenceDTO> daeAndOccurrencesComparator = Comparator.comparingInt(DAEAndOccurrenceDTO::getDae);

        return result.stream().sorted(daeAndOccurrencesComparator).collect(Collectors.toList());
    }

}
