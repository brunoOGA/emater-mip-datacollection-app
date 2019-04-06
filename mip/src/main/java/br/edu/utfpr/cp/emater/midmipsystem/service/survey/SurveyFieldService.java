package br.edu.utfpr.cp.emater.midmipsystem.service.survey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.SurveyField;
import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.SurveyFieldRepository;

@Component
public class SurveyFieldService {

    @Autowired
    private SurveyFieldRepository surveyFieldRepository;

    // public SurveyFieldService(SurveyFieldRepository surveyFieldRepository) {
    //     this.surveyFieldRepository = surveyFieldRepository;
    // }

    public List<SurveyField> listAllSurveyFields () {
        return List.copyOf(surveyFieldRepository.findAll());
    }
}