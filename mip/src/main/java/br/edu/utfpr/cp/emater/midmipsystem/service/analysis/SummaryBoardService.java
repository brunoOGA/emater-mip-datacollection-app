package br.edu.utfpr.cp.emater.midmipsystem.service.analysis;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDRustSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.service.mid.MIDRustSampleService;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummaryBoardService {

    private final SurveyService surveyService;
    private final MIPSampleService mipSampleService;
    private final MIDRustSampleService midSampleService;

    public List<SummaryBoardDTO> getSummaryBoardData(Region aLoggedUserRegion) {

        List<SummaryBoardDTO> result = new ArrayList<>();

        // To retrieve survey for the specified region
        var surveys = surveyService.readByRegion(aLoggedUserRegion);

        surveys.forEach(currentSurvey -> {

            // To retrieve MIPSamples for the specified survey
            var samplesMIP = mipSampleService.readBySurvey(currentSurvey);

            var minDateSampleMIP = new Date(samplesMIP.stream()
                    .flatMap(currentSample -> currentSample.getSampleDateAsOptional().stream())
                    .mapToLong(Date::getTime)
                    .sorted().min().orElse(0));

            var maxDateSampleMIP = new Date(samplesMIP.stream()
                    .flatMap(currentSample -> currentSample.getSampleDateAsOptional().stream())
                    .mapToLong(Date::getTime)
                    .sorted().max().orElse(0));

            // To retrieve MIDSamples for the specified survey
            var samplesMID = midSampleService.readBySurvey(currentSurvey);

            var minDateSampleMID = new Date(samplesMID.stream()
                    .flatMap(currentSample -> currentSample.getSampleDateAsOptional().stream())
                    .mapToLong(Date::getTime)
                    .sorted().min().orElse(0));

            var maxDateSampleMID = new Date(samplesMID.stream()
                    .flatMap(currentSample -> currentSample.getSampleDateAsOptional().stream())
                    .mapToLong(Date::getTime)
                    .sorted().max().orElse(0));

            // To extract data
            result.add(
                    SummaryBoardDTO.builder()
                            .name(currentSurvey.getFieldName())
                            .cityName(currentSurvey.getCity().map(City::getName).get())
                            .emergenceDate(currentSurvey.getEmergenceDate())
                            .quantitySamplesMIP(samplesMIP.size())
                            .dateFirstSampleMIP(minDateSampleMIP)
                            .dateLastSampleMIP(maxDateSampleMIP)
                            .quantityApplicationsMID(samplesMID.size())
//                            .sporePresentMID()
                            .dateFirstSampleMID(minDateSampleMID)
                            .dateLastSampleMID(maxDateSampleMID)
                            .build()
            );
        });

        return result;
    }

}
