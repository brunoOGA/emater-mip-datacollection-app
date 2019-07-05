package br.edu.utfpr.cp.emater.midmipsystem.service.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.service.mip.*;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestDisease;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestNaturalPredator;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Product;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.PulverisationOperation;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Target;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.TargetCategory;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.SupervisorNotAllowedInCity;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.MIPSampleRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation.PulverisationOperationRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class PulverisationOperationService {
    
    private final PulverisationOperationRepository pulverisationOperationRepository;
    private final TargetService targetService;
    private final ProductService productService;

    private final MIPSampleRepository mipSampleRepository;
    private final SurveyService surveyService;
    private final PestService pestService;
    private final PestDiseaseService pestDiseaseService;
    private final PestNaturalPredatorService pestNaturalPredatorService;

    @Autowired
    public PulverisationOperationService(PulverisationOperationRepository aPulverisationOperationRepository,
            TargetService aTargetService,
            ProductService aProductService,
            
            MIPSampleRepository aMIPSampleRepository,
            SurveyService aSurveyService,
            PestService aPestService,
            PestDiseaseService aPestDiseaseService,
            PestNaturalPredatorService aPestNaturalPredatorService) {

        this.mipSampleRepository = aMIPSampleRepository;
        this.surveyService = aSurveyService;
        this.pestService = aPestService;
        this.pestDiseaseService = aPestDiseaseService;
        this.pestNaturalPredatorService = aPestNaturalPredatorService;
        
        this.pulverisationOperationRepository = aPulverisationOperationRepository;
        this.targetService = aTargetService;
        this.productService = aProductService;
    }

    public List<MIPSample> readAll() {
        return List.copyOf(mipSampleRepository.findAll());
    }

    public List<Survey> readAllSurveysUniqueEntries() {
        return List.copyOf(pulverisationOperationRepository.findAll().stream().map(PulverisationOperation::getSurvey).distinct().collect(Collectors.toList()));
    }

    public void create(MIPSample aSample) throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {

        if (mipSampleRepository.findAll().stream().anyMatch(currentSample -> currentSample.equals(aSample))) {
            throw new EntityAlreadyExistsException();
        }

        var theSurvey = surveyService.readById(aSample.getSurvey().getId());

        aSample.setSurvey(theSurvey);

        try {
            mipSampleRepository.save(aSample);

        } catch (Exception e) {
            throw new AnyPersistenceException();

        }

    }

    public MIPSample readById(Long anId) throws EntityNotFoundException {
        return mipSampleRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
    }

    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        
        var existentOperation = pulverisationOperationRepository.findById(anId).orElseThrow(EntityNotFoundException::new);

        try {
            pulverisationOperationRepository.delete(existentOperation);

        } catch (DataIntegrityViolationException cve) {
            throw new EntityInUseException();

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }

    }

    public Survey readSurveyById(Long id) throws EntityNotFoundException {
        return surveyService.readById(id);
    }

    public List<Pest> readAllPests() {
        return pestService.readAll();
    }

    public List<PestDisease> readAllPestDiseases() {
        return pestDiseaseService.readAll();
    }

    public List<PestNaturalPredator> readAllPestNaturalPredators() {
        return pestNaturalPredatorService.readAll();
    }

    public List<PulverisationOperation> readAllPulverisationOperationBySurveyId(Long aSurveyId) {
        return List.copyOf(pulverisationOperationRepository.findAll().stream().filter(current -> current.getSurvey().getId().equals(aSurveyId)).collect(Collectors.toList()));
    }

    public List<Target> readAllTargetsByCategory(TargetCategory targetCategory) {
        return targetService.readAll().stream().filter(current -> current.getCategory().equals(targetCategory)).collect(Collectors.toList());
    }

    public List<Product> readAllProductByTarget(Long targetId) {
        return productService.readAll().stream().filter(current -> current.getTargetId().equals(targetId)).collect(Collectors.toList());
    }

    public Product readProductById(Long productId) throws EntityNotFoundException {
        return productService.readById(productId);
    }

    public Target readTargetById(Long targetId) throws EntityNotFoundException {
        return targetService.readById(targetId);
    }
}
