package br.edu.utfpr.cp.emater.midmipsystem.view.mid;

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

        return this.environment.getProperty("app.view.route.mide.sample.rust-monitoring.create.success");
    }

    @RequestMapping (value = "/list", method = RequestMethod.GET)
    public String listAll (@RequestParam int surveyFieldId, Model data) {
        data.addAttribute("success", false);

        return this.environment.getProperty("app.view.route.template.sample.list.rust-monitoring");
    }
}