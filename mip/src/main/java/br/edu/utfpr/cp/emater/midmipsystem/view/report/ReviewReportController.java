package br.edu.utfpr.cp.emater.midmipsystem.view.report;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDRustSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.PulverisationOperation;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.chart.DAEAndOccurrenceDTO;
import br.edu.utfpr.cp.emater.midmipsystem.service.report.ReportService;
import br.edu.utfpr.cp.emater.midmipsystem.service.report.ReviewReportDTO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@ViewScoped
@Getter
@RequiredArgsConstructor
public class ReviewReportController implements Serializable {

    private String cityName;
    private String farmerName;
    private List<String> supervisorNames;
    private String cultivarName;
    private boolean bt;
    private boolean rustResistant;
    private double totalArea;
    private boolean sporeCollectorPresent;
    private Date sowedDate;
    private Date emergenceDate;
    private double productivityField;
    private double productivityFarmer;

    private List<DAEAndOccurrenceDTO> defoliationChartData;

    private int quantitySamplesMIP;
    private Date dateFirstSampleMIP;
    private Date dateLastSampleMIP;
    private long quantityApplicationsInseticidaMIP;
    private long quantityApplicationsInseticidaBiologicoMIP;
    private Map<String, List<DAEAndOccurrenceDTO>> caterpillarChartData;
    private Map<String, List<DAEAndOccurrenceDTO>> bedBugChartData;
    private Map<String, List<DAEAndOccurrenceDTO>> naturalPredatorChartData;

    private int quantitySamplesMID;
    private boolean sporePresentMID;
    private Date dateFirstSampleMID;
    private Date dateLastSampleMID;
    private long quantityApplicationsMID;

    private List<MIPSample> mipSamples;
    private List<MIDRustSample> midRustSamples;
    private List<PulverisationOperation> pulverisationOperationSamples;

    private final ReportService reportService;

    @PostConstruct
    public void init() {

        var result = reportService.createReviewReport(158L).orElse(ReviewReportDTO.builder().build());

        this.cityName = result.getCityName();
        this.farmerName = result.getFarmerName();
        this.supervisorNames = result.getSupervisorNames();
        this.cultivarName = result.getCultivarName();
        this.bt = result.isBt();
        this.rustResistant = result.isRustResistant();
        this.totalArea = result.getTotalArea();
        this.sporeCollectorPresent = result.isSporeCollectorPresent();
        this.sowedDate = result.getSowedDate();
        this.emergenceDate = result.getEmergenceDate();
        this.productivityField = result.getProductivityField();
        this.productivityFarmer = result.getProductivityFarmer();

        this.quantitySamplesMIP = result.getQuantitySamplesMIP();
        this.dateFirstSampleMIP = result.getDateFirstSampleMIP();
        this.dateLastSampleMIP = result.getDateLastSampleMIP();
        this.quantityApplicationsInseticidaMIP = result.getQuantityApplicationsInseticidaMIP();
        this.quantityApplicationsInseticidaBiologicoMIP = result.getQuantityApplicationsInseticidaBiologicoMIP();

        this.quantitySamplesMID = result.getQuantitySamplesMID();
        this.sporePresentMID = result.isSporePresentMID();
        this.dateFirstSampleMID = result.getDateFirstSampleMID();
        this.dateLastSampleMID = result.getDateLastSampleMID();
        this.quantityApplicationsMID = result.getQuantityApplicationsMID();
        
        this.mipSamples = result.getMipSamples();

    }

}
