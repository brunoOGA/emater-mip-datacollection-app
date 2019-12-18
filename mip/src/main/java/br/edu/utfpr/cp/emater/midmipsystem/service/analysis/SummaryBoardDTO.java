package br.edu.utfpr.cp.emater.midmipsystem.service.analysis;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class SummaryBoardDTO {
    
    private final String name;
    private final String cityName;
    private final Date emergenceDate;
    private final int quantitySamplesMIP;
    private final Date dateFirstSampleMIP;
    private final Date dateLastSampleMIP;
    private final int quantityApplicationsInseticidaMIP;
    private final int quantityApplicationsInseticidaBiologicoMIP;
    private final int quantitySamplesMID;
    private final boolean sporePresentMID;
    private final Date dateFirstSampleMID;
    private final Date dateLastSampleMID;
    private final int quantityApplicationsMID;
}
