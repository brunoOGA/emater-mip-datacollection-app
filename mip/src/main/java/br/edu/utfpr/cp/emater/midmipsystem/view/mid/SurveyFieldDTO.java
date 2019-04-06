package br.edu.utfpr.cp.emater.midmipsystem.view.mid;

import java.io.Serializable;

import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.SurveyField;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SurveyFieldDTO implements Serializable {

    private Long surveyFieldId;
    private String harvestName;
    private String fieldName;
    private String fieldLocation;
    private String fieldCityName;
    private String seedName;
    private String farmerName;
    private String supervisorNames[];
}