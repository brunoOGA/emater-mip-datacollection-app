package br.edu.utfpr.cp.emater.midmipsystem.view.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyService;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@ViewScoped
@Component ("urDashboardController")
@RequiredArgsConstructor
public class URDashboardController implements Serializable {
    
    @Getter
    private Long surveyId;
    
    private final SurveyService surveyService;
    
    public void selectTargetSurvey(Long aSurveyId) {
        this.surveyId = aSurveyId;    
    }
    
    public String getSurveyName() throws EntityNotFoundException {
        return surveyService.readById(this.surveyId).getFieldName();
    }
    
}
