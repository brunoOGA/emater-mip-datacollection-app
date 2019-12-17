package br.edu.utfpr.cp.emater.midmipsystem.service.analysis.chart;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.DAEAndOccurrenceDTO;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MIPPestChartGeneratorService extends AbstractMIPChartGeneratorService<Pest> {

    public MIPPestChartGeneratorService() {
        super();

        this.retrieveDataFunction = (aSampleList, aPestList) -> {
            var result = new HashMap<Pest, List<DAEAndOccurrenceDTO>>();

            aPestList.forEach(currentPest
                    -> result.put(currentPest,
                            aSampleList.stream()
                                    .filter(currentSample -> currentSample.getDAEAndPestOccurrenceByPest(currentPest).isPresent())
                                    .map(currentSample -> currentSample.getDAEAndPestOccurrenceByPest(currentPest).get())
                                    .collect(Collectors.toList())
                    )
            );

            return result;
        };

    }
}
