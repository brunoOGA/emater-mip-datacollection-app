package br.edu.utfpr.cp.emater.midmipsystem.view.mip.detail;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSampleNaturalPredatorOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSamplePestDiseaseOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSamplePestOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleService;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component(value = "mipSampleDetailController")
@SessionScope
@RequiredArgsConstructor
public class MIPSampleDetailController {

    private final MIPSampleService mipSampleService;

    @Setter
    @Getter
    private Survey currentSurvey;

    @Setter
    @Getter
    private double value;

    public void onPestOccurrenceEdit(RowEditEvent event) {
        var currentMIPSample = (MIPSample) event.getComponent().getAttributes().get("currentMIPSample");

        var pestOccurrence = (MIPSamplePestOccurrence) event.getObject();

        pestOccurrence.setValue(this.getValue());

        try {
            mipSampleService.update(currentMIPSample);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Edição feita!", "" + pestOccurrence.getValue()));

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Edição nao salva!", "" + pestOccurrence.getValue()));
        }
    }

    public void onPestDiseaseOccurrenceEdit(RowEditEvent event) {
        var currentMIPSample = (MIPSample) event.getComponent().getAttributes().get("currentMIPSample");

        var pestDiseaseOccurrence = (MIPSamplePestDiseaseOccurrence) event.getObject();

        pestDiseaseOccurrence.setValue(this.getValue());

        try {
            mipSampleService.update(currentMIPSample);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Edição feita!", "" + pestDiseaseOccurrence.getValue()));

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Edição nao salva!", "" + pestDiseaseOccurrence.getValue()));
        }
    }

    public void onPestNaturalPredatorOccurrenceEdit(RowEditEvent event) {
        var currentMIPSample = (MIPSample) event.getComponent().getAttributes().get("currentMIPSample");

        var pestNaturalPredatorOccurrence = (MIPSampleNaturalPredatorOccurrence) event.getObject();

        pestNaturalPredatorOccurrence.setValue(this.getValue());

        try {
            mipSampleService.update(currentMIPSample);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Edição feita!", "" + pestNaturalPredatorOccurrence.getValue()));

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Edição nao salva!", "" + pestNaturalPredatorOccurrence.getValue()));
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Edição cancelada", "Cancelada!"));
    }

    public List<MIPSample> readAllMIPSampleBySurvey() {
        return mipSampleService.readAllMIPSampleBySurveyId(this.getCurrentSurvey().getId());
    }

    public String selectTargetSurvey(Long id) {

        try {
            this.setCurrentSurvey(mipSampleService.readSurveyById(id));

            return "/mip/mip-sample/sample-detail/view-sample-details.xhtml";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Anotação de campo não pode ser feita porque a UR não foi encontrada na base de dados!"));
            return "index.xhtml";
        }
    }

    public int calculateDAE(MIPSample aSample) {
        return aSample.getDAE();
    }

    @Deprecated
    public int calculateDaysAfterEmergence(Date sampleDate) {

        if (currentSurvey.getEmergenceDate() == null) {
            return 0;
        }

        long diffInMillies = (sampleDate.getTime() - currentSurvey.getEmergenceDate().getTime());

        if (diffInMillies > 0) {
            var result = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            return (int) (result + 1);

        } else {
            return 0;
        }
    }
}
