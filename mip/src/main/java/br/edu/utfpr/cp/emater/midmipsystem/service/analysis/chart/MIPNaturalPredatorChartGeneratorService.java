package br.edu.utfpr.cp.emater.midmipsystem.service.analysis.chart;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestNaturalPredator;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.DAEAndOccurrenceDTO;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MIPNaturalPredatorChartGeneratorService extends AbstractMIPChartGeneratorService<PestNaturalPredator> {

    public MIPNaturalPredatorChartGeneratorService() {
        super();

        this.retrieveDataFunction = (aSampleList, aTargetList) -> {
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
        };

    }
}
