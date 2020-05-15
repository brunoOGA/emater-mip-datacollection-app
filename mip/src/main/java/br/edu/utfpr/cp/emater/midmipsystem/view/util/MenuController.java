package br.edu.utfpr.cp.emater.midmipsystem.view.util;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@SessionScoped
@RequiredArgsConstructor
public class MenuController implements Serializable {

    private final SurveyService surveyService;

    @Getter
    private List<MenuEntry> myURsList;

    @PostConstruct
    public void init() {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);

        var currentSupervisor = ((Supervisor) session.getAttribute("currentSupervisor"));

        if (currentSupervisor != null) {

            var surveys = surveyService.readBySupervisorId(currentSupervisor.getId());

            if (surveys != null) {
                if (surveys.size() > 0) {
                    this.myURsList = new ArrayList<>();

                    surveys.forEach(currentSurvey
                            -> this.myURsList.add(
                                    MenuEntry.builder()
                                            .title(currentSurvey.getFieldName())
                                            .url(
                                                 String.format("/dashboard/ur-dashboard.xhtml?surveyId=%s&fieldName=%s&harvestName=%s",
                                                         currentSurvey.getId(),
                                                         currentSurvey.getFieldName(),
                                                         currentSurvey.getHarvestName())   
                                                 )
                                            .build()
                            )
                    );
                }
            }
        }
    }
}
