package br.edu.utfpr.cp.emater.midmipsystem.view.mid;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping (value = "/mid/rust-monitoring/sample")
public class RustMonitoringSampleController {

    private final Environment environment;

    private boolean operationSuccessMessage;

    private void resetOperationSuccessMessage() {
        if (this.operationSuccessMessage)
            this.operationSuccessMessage = false;
    }

    private void setOperationSuccessMessage() {
        this.operationSuccessMessage = true;
    }

    @Autowired
    public RustMonitoringSampleController(Environment environment) {
        this.environment = environment;
        this.operationSuccessMessage = false;
    }

    @RequestMapping (value = "/create", method = RequestMethod.POST)
    public String create (RustMonitoringSampleDTO sample) {

        System.out.println(sample);

        return this.environment.getProperty("app.view.route.mid.sample.rust-monitoring.create.success");
    }

    @RequestMapping (value = "/list", method = RequestMethod.GET)
    public String listAll (@RequestParam int surveyFieldId, Model data) {

        RustMonitoringSampleDTO sample = RustMonitoringSampleDTO.builder()
                .asiaticRustApplication(true)
                .bladeInstalledPreCold(true)
                .bladeReadingDate(LocalDate.now())
                .bladeReadingRustResultCollector(RustMonitoringSampleDTO.AsiaticRustTypesSporeCollector.FEASIBLE_SPORES_GROUPED.getDescription())
                .bladeReadingRustResultInspection(RustMonitoringSampleDTO.AsiaticRustTypesInspection.VISIBLE_DAMAGE_ALL_CROP.getDescription())
                .collectorInstallDate(LocalDate.now())
                .colletionDate(LocalDate.now())
                .fungicideApplicationDate(LocalDate.now())
                .fungicideNotes("notes ...")
                .growthPhase(RustMonitoringSampleDTO.GrowthPhase.R5_2.getDescription())
                .otherDiseasesApplication(true)
                .readingBladeResponsibleName("John")
                .readingBladeResponsibleNameEntity("Entity")
                .surveyFieldId(12)
                .build();

        var rustMonitoringSamples = List.of(sample, sample, sample);

        data.addAttribute("rustMonitoringSamples", rustMonitoringSamples);
        data.addAttribute("success", false);
        data.addAttribute("urlDelete", "/mid/rust-monitoring/sample/delete");
        data.addAttribute("farmerName", "John Farmer");
        data.addAttribute("harvestName", "Safra 2019/2020");

        return this.environment.getProperty("app.view.route.template.sample.list.rust-monitoring");
    }

    @RequestMapping (value = "/delete", method = RequestMethod.POST)
    public String delete (@RequestParam int surveyFieldId) {

        System.out.println("Deleting survey field id = " + surveyFieldId);

        return environment.getProperty("app.view.route.mid.sample.rust-monitoring.delete.success");
    }
}