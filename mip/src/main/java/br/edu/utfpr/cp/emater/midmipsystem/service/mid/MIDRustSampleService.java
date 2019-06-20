package br.edu.utfpr.cp.emater.midmipsystem.service.mid;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDRustSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mid.MIDRustSampleRepository;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MIDRustSampleService {

    private final MIDRustSampleRepository midRustSampleRepository;

    private final SurveyService surveyService;

    @Autowired
    public MIDRustSampleService(MIDRustSampleRepository midRustSampleRepository,
            SurveyService aSurveyService) {

        this.midRustSampleRepository = midRustSampleRepository;
        this.surveyService = aSurveyService;
    }

    public Survey readSurveyById(Long id) throws EntityNotFoundException {
        return surveyService.readById(id);
    }

    public List<Survey> readAllSurveysUniqueEntries() {
        return midRustSampleRepository.findAll().stream().map(MIDRustSample::getSurvey).distinct().collect(Collectors.toList());
    }

    public void create(MIDRustSample aSample) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        
        if (midRustSampleRepository.findAll().stream().anyMatch(currentSample -> currentSample.equals(aSample))) {
            throw new EntityAlreadyExistsException();
        }

        var theSurvey = surveyService.readById(aSample.getSurvey().getId());

        aSample.setSurvey(theSurvey);

        try {
            midRustSampleRepository.save(aSample);

        } catch (Exception e) {
            throw new AnyPersistenceException();

        }
    }
}
