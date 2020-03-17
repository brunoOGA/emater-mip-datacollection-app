package br.edu.utfpr.cp.emater.midmipsystem.view.report;

import java.util.Date;

import javax.faces.view.ViewScoped;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Component
@ViewScoped
@Getter
public class SimpleCheckReport {

    private String cityName;
    private String farmerName;
    private String supervisorsName;
    private String cultivarName;
    private boolean bt;
    private boolean rustResitant;
    private double totalArea;
    private double totalPlantedArea;
    private boolean sporeCollectorPresent;
    private Date sowedDate;
    private Date emergenceDate;
    private double productivityField;
    private double productivityFarmer;
    
}