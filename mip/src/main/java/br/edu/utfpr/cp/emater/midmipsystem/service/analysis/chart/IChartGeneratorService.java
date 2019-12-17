package br.edu.utfpr.cp.emater.midmipsystem.service.analysis.chart;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.DAEAndOccurrenceDTO;
import java.util.List;
import java.util.Map;

public interface IChartGeneratorService<T> {

    public List<DAEAndOccurrenceDTO> of(List<MIPSample> aSampleList);
    public Map<String, List<DAEAndOccurrenceDTO>> of(List<MIPSample> aSampleList, List<T> aTargetList);

    public List<DAEAndOccurrenceDTO> retrieveData(List<MIPSample> samples);
    public Map<T, List<DAEAndOccurrenceDTO>> retrieveData(List<MIPSample> aSampleList, List<T> aTargetList);
    
    public Map<Integer, Double> filter(List<DAEAndOccurrenceDTO> occurrences);
    public Map<T, Map<Integer, Double>> filter(Map<T, List<DAEAndOccurrenceDTO>> anOccurrenceList);
    
    public List<DAEAndOccurrenceDTO> format(Map<Integer, Double> input);
    public Map<String, List<DAEAndOccurrenceDTO>> format(Map<T, Map<Integer, Double>> input);

}
