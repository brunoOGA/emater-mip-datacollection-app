package br.edu.utfpr.cp.emater.midmipsystem.view.mid;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.core.env.Environment;

@Controller
@RequestMapping (value = "/mid/rust-monitoring")
public class RustMonitoringController {

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
    public RustMonitoringController(Environment environment) {
        this.environment = environment;
        this.operationSuccessMessage = false;
    }
    
    @RequestMapping (value = "/list", method = RequestMethod.GET)
    public String listAll(Model data) {

        var bladeReading = RustMonitoringDTO.builder()
            .surveyFieldId(new Long(1))
            .farmerName("John Farmer")
            .fieldCityName("Apucarana")
            .fieldLocation("Hope place")
            .fieldName("My farm")
            .harvestName("Safra 2018/2019")
            .seedName("Special Seed")
            .supervisorNames(new String[] {"John", "Michel"})
            .build();
        
        List<RustMonitoringDTO> bladeReadingList = List.of(bladeReading, bladeReading, bladeReading);

        data.addAttribute("rustMonitoringList", bladeReadingList);
        data.addAttribute("success", this.operationSuccessMessage);

        this.resetOperationSuccessMessage();
                
        return this.environment.getProperty("app.view.route.template.list.mid.rust-monitoring");
    }

    @RequestMapping (value = "/create", method = RequestMethod.GET)
    public String create(@RequestParam int surveyFieldId, Model data) {
        data.addAttribute("asiaticRustTypesSpore", RustMonitoringSampleDTO.AsiaticRustTypesSporeCollector.values());
        data.addAttribute("asiaticRustTypesInspection", RustMonitoringSampleDTO.AsiaticRustTypesInspection.values());
        data.addAttribute("growthPhases", RustMonitoringSampleDTO.GrowthPhase.values());

        return this.environment.getProperty("app.view.route.template.sample.create.rust-monitoring"); 
    }
}