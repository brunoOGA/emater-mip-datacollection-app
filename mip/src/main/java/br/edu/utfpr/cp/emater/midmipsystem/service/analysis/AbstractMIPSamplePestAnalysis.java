package br.edu.utfpr.cp.emater.midmipsystem.service.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import java.util.List;
import org.primefaces.model.chart.LineChartModel;


public abstract class AbstractMIPSamplePestAnalysis extends AbstractMIPSampleAnalysis {

    public AbstractMIPSamplePestAnalysis(MIPSampleService mipSampleService) {
        super(mipSampleService);
    }

    public LineChartModel getPestLineChart(List<MIPSample> MIPSampleData) {
        var pests = this.getPests();

        var pestAndDAEAndOccurrences = getDAEAndOccurrences(pests, MIPSampleData);

        var pestAndDAEAndOccurrencesMap = consolidateDAEAndOccurrences(pestAndDAEAndOccurrences);

        var chartSeries = getChartSeries(pestAndDAEAndOccurrencesMap);

        var chartModel = new LineChartModel();
        chartSeries.forEach(chartModel::addSeries);
        this.setLineChartInfo(chartModel);

        return chartModel;
    }

    public LineChartModel getPestLineChart() {
        var samples = this.getSamples();

        var pests = this.getPests();

        var pestAndDAEAndOccurrences = getDAEAndOccurrences(pests, samples);

        var pestAndDAEAndOccurrencesMap = consolidateDAEAndOccurrences(pestAndDAEAndOccurrences);

        var chartSeries = getChartSeries(pestAndDAEAndOccurrencesMap);

        var chartModel = new LineChartModel();
        chartSeries.forEach(chartModel::addSeries);
        this.setLineChartInfo(chartModel);

        return chartModel;
    }

    protected abstract List<Pest> getPests();

}
