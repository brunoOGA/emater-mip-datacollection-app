package br.edu.utfpr.cp.emater.midmipsystem.service.analysis;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryBoardDTO {
    
    private String name;
    private String cityName;
    private Date emergenceDate;
    private int quantitySamplesMIP;
    private Date dateFirstSampleMIP;
    private Date dateLastSampleMIP;
    private int quantityApplicationsInseticidaMIP;
    private int quantityApplicationsInseticidaBiologicoMIP;
    private int quantitySamplesMID;
    private boolean sporePresentMID;
    private Date dateFirstSampleMID;
    private Date dateLastSampleMID;
    private int quantityApplicationsMID;
}
