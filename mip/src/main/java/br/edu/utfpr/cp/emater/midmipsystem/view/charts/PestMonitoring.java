package br.edu.utfpr.cp.emater.midmipsystem.view.charts;

import java.util.List;
import java.util.Map;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class PestMonitoring {

    public LineChartModel createCaterpillarFluctuationChart() {
        var result = new LineChartModel();

        result.setTitle("Flutuação de Lagartas");
        result.setLegendPosition("e");
        result.setShowPointLabels(true);

        Axis xAxis = result.getAxis(AxisType.X);
        xAxis.setLabel("Dias Após Emergência");
        xAxis.setMin(0);
        xAxis.setMax(10);

        Axis yAxis = result.getAxis(AxisType.Y);
        yAxis.setLabel("No. Insetos/metro");
        yAxis.setMin(0);
        yAxis.setMax(10);

        var series = List.of("Anticarsia sp. (> 1,5 cm)", "Chrysodeixis ssp. (> 1,5 cm)", "Spodoptera ssp. (< & > 1,5 cm)", "Heliothinae (< & > 1,5 cm)");

        var seriesValues = Map.of(1, 2, 2, 1, 3, 3, 4, 6, 5, 8);

        series.forEach(e -> {
            var serie = new LineChartSeries();
            serie.setLabel(e);

            seriesValues.keySet().forEach(s -> serie.set(s, seriesValues.get(s)));

            result.addSeries(serie);
        });
        
        return result;
    }

}
