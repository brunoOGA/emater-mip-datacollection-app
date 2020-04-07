package br.edu.utfpr.cp.emater.midmipsystem.service.report;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDRustSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.PulverisationOperationOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.analysis.AnalysisService;
import br.edu.utfpr.cp.emater.midmipsystem.service.mid.MIDRustSampleService;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import br.edu.utfpr.cp.emater.midmipsystem.service.pulverisation.PulverisationOperationService;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class ReportService {

    private final SurveyService surveyService;
    private final AnalysisService analysisService;
    private final MIPSampleService mipService;
    private final PulverisationOperationService pulverisationService;
    private final MIDRustSampleService midRustService;

    private OptionalLong getMinMIDDate(List<MIDRustSample> midSamples) {
        return midSamples.stream()
                .map(MIDRustSample::getSampleDateAsOptional)
                .map(Optional::get)
                .mapToLong(Date::getTime)
                .min();
    }

    private OptionalLong getMaxMIDDate(List<MIDRustSample> midSamples) {
        return midSamples.stream()
                .map(MIDRustSample::getSampleDateAsOptional)
                .map(Optional::get)
                .mapToLong(Date::getTime)
                .max();
    }

    private OptionalLong getMinMIPDate(List<MIPSample> mipSamples) {
        return mipSamples.stream()
                .map(MIPSample::getSampleDateAsOptional)
                .map(Optional::get)
                .mapToLong(Date::getTime)
                .min();
    }

    private OptionalLong getMaxMIPDate(List<MIPSample> mipSamples) {
        return mipSamples.stream()
                .map(MIPSample::getSampleDateAsOptional)
                .map(Optional::get)
                .mapToLong(Date::getTime)
                .max();
    }

    public Optional<ReviewReportDTO> createReviewReport(Long surveyId) {

        try {
            var currentSurvey = surveyService.readById(surveyId);

            var mipSamples = mipService.readAllMIPSampleBySurveyId(surveyId);
            var midSamples = midRustService.readAllMIPSampleBySurveyId(surveyId);
            var pulverisationSamples = pulverisationService.readAllPulverisationOperationBySurveyId(surveyId);
            
            var defoliationChartData = analysisService.getDefoliationChart(mipSamples);
            var caterpillarChartData = analysisService.getCaterpillarChart(mipSamples);
            var bedbugChartData = analysisService.getBedBugChart(mipSamples);
            var naturalPredatorChartData = analysisService.getNaturalPredatorChart(mipSamples);

            var minMIPDate = this.getMinMIPDate(mipSamples);
            var minMIDDate = this.getMinMIDDate(midSamples);

            var maxMIPDate = this.getMaxMIPDate(mipSamples);
            var maxMIDDate = this.getMaxMIDDate(midSamples);

            return Optional.of(
                ReviewReportDTO.builder()
                    .cityName(currentSurvey.getCity().map(City::getName).orElse("Indefinda"))
                    .farmerName(currentSurvey.getFarmerString())
                    .supervisorNames(
                            Optional.ofNullable(
                                    currentSurvey.getField()
                            ).map(Field::getSupervisorNames)
                                    .orElse(Stream.of("Indefinido")
                                            .collect(Collectors.toList()))
                    )
                    .cultivarName(currentSurvey.getCultivarName())
                    .bt(currentSurvey.isBt())
                    .rustResistant(currentSurvey.isRustResistant())
                    .totalArea(currentSurvey.getTotalArea())
                    .sporeCollectorPresent(currentSurvey.isSporeCollectorPresent())
                    .sowedDate(currentSurvey.getSowedDate())
                    .emergenceDate(currentSurvey.getEmergenceDate())
                    .productivityField(currentSurvey.getProductivityField())
                    .productivityFarmer(currentSurvey.getProductivityFarmer())
                    .defoliationChartData(defoliationChartData)
                    .quantitySamplesMIP(mipSamples.size())
                    .dateFirstSampleMIP(minMIPDate.isPresent() ? new Date(minMIPDate.getAsLong()) : null)
                    .dateLastSampleMIP(maxMIPDate.isPresent() ? new Date(maxMIPDate.getAsLong()) : null)
                        
                    .quantityApplicationsInseticidaMIP(
                            pulverisationSamples.stream()
                            .filter(currentPulverisation -> 
                                    currentPulverisation.isTargetMIP() == true)
                            .count()
                    )
                    .quantityApplicationsInseticidaBiologicoMIP(
                            pulverisationSamples.stream()
                                    .filter(currentPulverisation -> 
                                            currentPulverisation.isInseticidaBiologico() == true)
                                    .count()
                    )
                    .caterpillarChartData(caterpillarChartData)
                    .bedBugChartData(bedbugChartData)
                    .naturalPredatorChartData(naturalPredatorChartData)
                    .quantitySamplesMID(midSamples.size())
                    .sporePresentMID(midSamples.stream()
                            .filter(currentSample -> currentSample.isSporePresent() == true)
                            .count() > 0 ? true : false
                    )
                    .dateFirstSampleMID(minMIDDate.isPresent() ? new Date(minMIDDate.getAsLong()) : null)
                    .dateLastSampleMID(maxMIDDate.isPresent() ? new Date(maxMIDDate.getAsLong()) : null)
                        
                    .quantityApplicationsMID(
                                pulverisationSamples.stream()
                                        .filter(
                                                currentPulverisation -> 
                                                        currentPulverisation.isTargetMID() == true)
                                        .count()
                    )
                    .mipSamples(mipSamples)
                    .midRustSamples(midSamples)
                    .pulverisationOperationSamples(pulverisationSamples)
                    .build()
            );

        } catch (Exception e) {
            return Optional.empty();
        }

    }

    public List<Survey> readSurveyBySupervisorId(Long surveyId) {
        return surveyService.readBySupervisorId(surveyId);
    }

    public List<Survey> readAllSurveys() {
        return surveyService.readAll();
    }
}
