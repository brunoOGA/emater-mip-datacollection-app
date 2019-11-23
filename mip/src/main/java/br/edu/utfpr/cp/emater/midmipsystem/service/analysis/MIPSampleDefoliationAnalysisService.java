package br.edu.utfpr.cp.emater.midmipsystem.service.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import java.util.List;
import org.primefaces.model.chart.LineChartModel;

public class MIPSampleDefoliationAnalysisService extends AbstractMIPSampleAnalysis {

    public MIPSampleDefoliationAnalysisService(MIPSampleService mipSampleService) {
        super(mipSampleService);
    }

    @Override
    public LineChartModel getChart() {
        var samples = this.getSamples();
        
//        var pestAndDAEAndOccurrences = getDAEAndOccurrences(pests, samples);
//
//        var pestAndDAEAndOccurrencesMap = consolidateDAEAndOccurrences(pestAndDAEAndOccurrences);
//
//        var chartSeries = getChartSeries(pestAndDAEAndOccurrencesMap);

        var chartModel = new LineChartModel();
//        chartSeries.forEach(chartModel::addSeries);
//        this.setLineChartInfo(chartModel);

        return chartModel;
    }

    @Override
    public LineChartModel getChart(List<MIPSample> MIPSampleData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
