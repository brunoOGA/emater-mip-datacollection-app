package br.edu.utfpr.cp.emater.midmipsystem.service.report;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDRustSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.PulverisationOperation;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.chart.DAEAndOccurrenceDTO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class ReviewReportDTO implements Serializable {

    private final String cityName;
    private final String farmerName;
    private final List<String> supervisorNames;
    private final String cultivarName;
    private final boolean bt;
    private final boolean rustResistant;
    private final double totalArea;
    private final boolean sporeCollectorPresent;
    private final Date sowedDate;
    private final Date emergenceDate;
    private final double productivityField;
    private final double productivityFarmer;
    
    private final List<DAEAndOccurrenceDTO> defoliationChartData;
    
    private final int quantitySamplesMIP;
    private final Date dateFirstSampleMIP;
    private final Date dateLastSampleMIP;
    private final long quantityApplicationsInseticidaMIP;
    private final long quantityApplicationsInseticidaBiologicoMIP;
    private final Map<String, List<DAEAndOccurrenceDTO>> caterpillarChartData;
    private final Map<String, List<DAEAndOccurrenceDTO>> bedBugChartData;
    private final Map<String, List<DAEAndOccurrenceDTO>> naturalPredatorChartData;
    
    private final int quantitySamplesMID;
    private final boolean sporePresentMID;
    private final Date dateFirstSampleMID;
    private final Date dateLastSampleMID;
    private final long quantityApplicationsMID;

    private final List<MIPSample> mipSamples;
    private final List<MIDRustSample> midRustSamples;
    private final List<PulverisationOperation> pulverisationOperationSamples;
}
