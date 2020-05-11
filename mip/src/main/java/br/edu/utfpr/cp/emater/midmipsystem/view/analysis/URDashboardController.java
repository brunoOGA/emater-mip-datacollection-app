package br.edu.utfpr.cp.emater.midmipsystem.view.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.primefaces.PrimeFaces;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@ViewScoped
//@SessionScope
@Component ("urDashboardController")
@RequiredArgsConstructor
public class URDashboardController implements Serializable {
    
    
    @Getter
    private List<String> dates = List.of("10/02/2020", "17/02/2020", "03/03/2020");
    
    @Getter
    private Long surveyId;
    
    private final SurveyService surveyService;
    
    public void selectTargetSurvey(Long aSurveyId) {
        this.surveyId = aSurveyId;    
    }
    
    public String getSurveyName() throws EntityNotFoundException {
        return surveyService.readById(this.surveyId).getFieldName();
    }
    
    public PieChartModel getPieChart() {
        
        PieChartModel pieModel = new PieChartModel();
        ChartData data = new ChartData();
         
        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(300);
        values.add(50);
        values.add(100);
        dataSet.setData(values);
         
        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);
         
        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Red");
        labels.add("Blue");
        labels.add("Yellow");
        data.setLabels(labels);
         
        pieModel.setData(data);
        
        return pieModel;
    }
    
    public LineChartModel getLineChart() {
        
        var lineModel = new LineChartModel();
        ChartData data = new ChartData();
         
        LineChartDataSet dataSet = new LineChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(65);
        values.add(59);
        values.add(80);
        values.add(81);
        values.add(56);
        values.add(55);
        values.add(40);
        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("My First Dataset");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setLineTension(0.1);
        data.addChartDataSet(dataSet);
         
        List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        data.setLabels(labels);
         
        //Options
        LineChartOptions options = new LineChartOptions();        
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Line Chart");
        options.setTitle(title);
         
        lineModel.setOptions(options);
        lineModel.setData(data);
        
        return lineModel;
    }
    
     public void viewCars() {
         Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
         PrimeFaces.current().dialog().openDynamic("add-mip", options, null);
    }
     
     public void doSomething(){
         System.out.println("Hello world?");
     }
    
}
