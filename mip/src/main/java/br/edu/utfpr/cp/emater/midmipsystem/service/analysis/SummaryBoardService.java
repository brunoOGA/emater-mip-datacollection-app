package br.edu.utfpr.cp.emater.midmipsystem.service.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDRustSample;
import br.edu.utfpr.cp.emater.midmipsystem.service.mid.MIDRustSampleService;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import br.edu.utfpr.cp.emater.midmipsystem.service.pulverisation.PulverisationOperationService;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummaryBoardService {

    private final SurveyService surveyService;
    private final MIPSampleService mipSampleService;
    private final MIDRustSampleService midSampleService;
    private final PulverisationOperationService pulverisationService;

    public SummaryBoardDTO getSummaryBoardRegionSummary(List<SummaryBoardDTO> aSummaryBoardList) {

        var minMIPLong = aSummaryBoardList.stream()
                .filter(currentItem -> currentItem.getDateFirstSampleMIP() != null)
                .mapToLong(currentItem -> currentItem.getDateFirstSampleMIP().getTime())
                .min();

        var maxMIPLong = aSummaryBoardList.stream()
                .filter(currentItem -> currentItem.getDateLastSampleMIP() != null)
                .mapToLong(currentItem -> currentItem.getDateLastSampleMIP().getTime())
                .max();

        var minMIDLong = aSummaryBoardList.stream()
                .filter(currentItem -> currentItem.getDateFirstSampleMID() != null)
                .mapToLong(currentItem -> currentItem.getDateFirstSampleMID().getTime())
                .min();

        var maxMIDLong = aSummaryBoardList.stream()
                .filter(currentItem -> currentItem.getDateLastSampleMID() != null)
                .mapToLong(currentItem -> currentItem.getDateLastSampleMID().getTime())
                .max();

        var minMIPDate = minMIPLong.isPresent() ? new Date(minMIPLong.getAsLong()) : null;
        var maxMIPDate = maxMIPLong.isPresent() ? new Date(maxMIPLong.getAsLong()) : null;
        var minMIDDate = minMIDLong.isPresent() ? new Date(minMIDLong.getAsLong()) : null;
        var maxMIDDate = maxMIDLong.isPresent() ? new Date(maxMIDLong.getAsLong()) : null;

        var result = SummaryBoardDTO.builder()
                .quantitySamplesMIP(aSummaryBoardList.stream().mapToInt(SummaryBoardDTO::getQuantitySamplesMIP).sum())
                .quantityApplicationsInseticidaMIP(aSummaryBoardList.stream().mapToInt(SummaryBoardDTO::getQuantityApplicationsInseticidaMIP).sum())
                .dateFirstSampleMIP(minMIPDate)
                .dateLastSampleMIP(maxMIPDate)
                .quantitySamplesMID(aSummaryBoardList.stream().mapToInt(SummaryBoardDTO::getQuantitySamplesMID).sum())
                .quantityApplicationsMID(aSummaryBoardList.stream().mapToInt(SummaryBoardDTO::getQuantityApplicationsMID).sum())
                .sporePresentMID(aSummaryBoardList.stream().map(currentItem -> currentItem.isSporePresentMID()).anyMatch(currentItem -> currentItem.equals(true)))
                .dateFirstSampleMID(minMIDDate)
                .dateLastSampleMID(maxMIDDate)
                .build();
        
        return result;
    }

    public List<SummaryBoardDTO> getSummaryBoardData(Region aLoggedUserRegion) {

        List<SummaryBoardDTO> result = new ArrayList<>();

        // To retrieve survey for the specified region
        var surveys = surveyService.readByRegion(aLoggedUserRegion);

        surveys.forEach(currentSurvey -> {

            // To retrieve MIPSamples for the specified survey
            var samplesMIP = mipSampleService.readBySurvey(currentSurvey);

            var minDateSampleMIPValue = samplesMIP.stream()
                    .flatMap(currentSample -> currentSample.getSampleDateAsOptional().stream())
                    .mapToLong(Date::getTime)
                    .sorted().min().orElse(0);

            var minDateSampleMIP = minDateSampleMIPValue == 0 ? null : new Date(minDateSampleMIPValue);

            var maxDateSampleMIPValue = samplesMIP.stream()
                    .flatMap(currentSample -> currentSample.getSampleDateAsOptional().stream())
                    .mapToLong(Date::getTime)
                    .sorted().max().orElse(0);

            var maxDateSampleMIP = maxDateSampleMIPValue == 0 ? null : new Date(maxDateSampleMIPValue);

            // To retrieve MIDSamples for the specified survey
            var samplesMID = midSampleService.readBySurvey(currentSurvey);

            var minDateSampleMIDValue = samplesMID.stream()
                    .flatMap(currentSample -> currentSample.getSampleDateAsOptional().stream())
                    .mapToLong(Date::getTime)
                    .sorted().min().orElse(0);

            var minDateSampleMID = minDateSampleMIDValue == 0 ? null : new Date(minDateSampleMIDValue);

            var maxDateSampleMIDValue = samplesMID.stream()
                    .flatMap(currentSample -> currentSample.getSampleDateAsOptional().stream())
                    .mapToLong(Date::getTime)
                    .sorted().max().orElse(0);

            var maxDateSampleMID = maxDateSampleMIDValue == 0 ? null : new Date(maxDateSampleMIDValue);

            // To retrieve Pulvarisation samples for the specified survey
            var pulverisationOperation = pulverisationService.readBySurvey(currentSurvey);

            // To extract data
            result.add(
                    SummaryBoardDTO.builder()
                            .name(currentSurvey.getFieldName())
                            .cityName(currentSurvey.getCity().map(City::getName).get())
                            .emergenceDate(currentSurvey.getEmergenceDate())
                            .quantitySamplesMIP(samplesMIP.size())
                            .dateFirstSampleMIP(minDateSampleMIP)
                            .dateLastSampleMIP(maxDateSampleMIP)
                            .quantityApplicationsInseticidaMIP(pulverisationOperation.stream().filter(currentPulverisation -> currentPulverisation.isTargetMIP() == true).collect(Collectors.toList()).size())
                            .quantityApplicationsInseticidaBiologicoMIP(pulverisationOperation.stream().filter(currentPulverisation -> currentPulverisation.isInseticidaBiologico() == true).collect(Collectors.toList()).size())
                            .quantitySamplesMID(samplesMID.size())
                            .sporePresentMID(samplesMID.stream().map(MIDRustSample::isSporePresent).anyMatch(currentResult -> currentResult.equals(true)))
                            .dateFirstSampleMID(minDateSampleMID)
                            .dateLastSampleMID(maxDateSampleMID)
                            .quantityApplicationsMID(pulverisationOperation.stream().filter(currentPulverisation -> currentPulverisation.isTargetMID() == true).collect(Collectors.toList()).size())
                            .build()
            );
        });

        return result;
    }

}
