package br.edu.utfpr.cp.emater.midmipsystem.service.mid;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.BladeReadingResponsiblePerson;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDRustSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mid.MIDRustSampleRepository;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MIDRustSampleService {

    private final MIDRustSampleRepository midRustSampleRepository;

    private final SurveyService surveyService;

    private final BladeReadingResponsiblePersonService bladeResponsiblePersonService;

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

    public List<MIDRustSample> readAllMIPSampleBySurveyId(Long id) {
        Comparator<MIDRustSample> compareSampleDate = Comparator.comparingLong(sample -> sample.getSampleDate().getTime());
        return List.copyOf(this.midRustSampleRepository.findAll().stream().filter(current -> current.getSurvey().getId().equals(id)).sorted(compareSampleDate.reversed()).collect(Collectors.toList()));
    }

    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException, AccessDeniedException {

        var existentSample = midRustSampleRepository.findById(anId).orElseThrow(EntityNotFoundException::new);

        try {
            midRustSampleRepository.delete(existentSample);

        } catch (AccessDeniedException cve) {
            throw cve;            

        } catch (DataIntegrityViolationException cve) {
            throw new EntityInUseException();

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }

    public List<BladeReadingResponsiblePerson> readAllBladeResponsiblePersons() {
        return bladeResponsiblePersonService.readAll();
    }

    public List<MIDRustSample> readBySurvey(Survey aSurvey) {
        return midRustSampleRepository.findBySurvey(aSurvey);
    }
}
