package br.edu.utfpr.cp.emater.midmipsystem.service.survey;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.SurveyField;
import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.SurveyFieldRepository;
import br.edu.utfpr.cp.emater.midmipsystem.view.mid.SurveyFieldDTO;

@Component
public class SurveyFieldService {

    private SurveyFieldRepository surveyFieldRepository;

    @Autowired
    public SurveyFieldService(SurveyFieldRepository surveyFieldRepository) {
        this.surveyFieldRepository = surveyFieldRepository;
    }

    public List<SurveyField> listAllSurveyFields () {
        return List.copyOf(surveyFieldRepository.findAll());
    }

    // It should return an immutable object!
    public Optional<SurveyField> findById (Long id) {
        return surveyFieldRepository.findById(id);
    }

    public SurveyFieldDTO convertFrom (SurveyField surveyField) {

        String supervisors[] = surveyField.getField().getSupervisors().stream().map(s -> s.getName()).collect(Collectors.toList()).toArray(new String[]{});

        return SurveyFieldDTO.builder()
                    .surveyFieldId(surveyField.getId())
                    .farmerName(surveyField.getField().getFarmer().getName())
                    .fieldCityName(surveyField.getField().getCity().getName())
                    .fieldLocation(surveyField.getField().getLocation())
                    .fieldName(surveyField.getField().getName())
                    .harvestName(surveyField.getHarvest().getName())
                    .seedName(surveyField.getSeedName())
                    .supervisorNames(supervisors)
                    .build();
    }
}