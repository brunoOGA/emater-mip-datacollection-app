package br.edu.utfpr.cp.emater.midmipsystem.view.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.MIPPestAnalysisService;
import lombok.RequiredArgsConstructor;
import org.primefaces.model.chart.LineChartModel;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component ("mipPestAnalysisController")
@RequestScope
@RequiredArgsConstructor
public class MIPPestAnalysisController {

    private final MIPPestAnalysisService analysisService;

    public LineChartModel createCaterpillarFluctuationChart() throws Exception {
        return analysisService.createCaterpillarFluctuationChart();
    }
}
