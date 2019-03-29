package br.edu.utfpr.cp.emater.midmipsystem.view.mip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// @Controller
// @RequestMapping (value = "/pest-survey/list-collected-samples")
public class MIPPestSurveyCollectedSampleController {

    // private final SamplePestRepository samplePestRepository;
    // private final MipPestSurveyRepository mipPestSurveyRepository;

    // private final Environment environment;

    // private boolean operationSuccessMessage;

    // private void resetOperationSuccessMessage() {
    //     if (this.operationSuccessMessage)
    //         this.operationSuccessMessage = false;
    // }

    // private void setOperationSuccessMessage() {
    //     this.operationSuccessMessage = true;
    // }


    // @Autowired
    // public SampleController(SamplePestRepository samplePestRepository, MipPestSurveyRepository mipPestSurveyRepository, Environment environment) {
    //     this.samplePestRepository = samplePestRepository;
    //     this.mipPestSurveyRepository = mipPestSurveyRepository;
    //     this.environment = environment;
    //     this.operationSuccessMessage = false;
    // }
    
    // @RequestMapping (value = "", method = RequestMethod.GET)
    // public String listAll(@RequestParam int mipPestSurveyId, Model data) {
    //     MipPestSurvey mipPestSurvey = mipPestSurveyRepository.findById(new Long(mipPestSurveyId)).orElseThrow();

    //     data.addAttribute("mipPestSurvey", mipPestSurvey);
    //     data.addAttribute("samplePests", samplePestRepository.findAll().stream().filter(e -> e.getMipPestSurvey().getId().equals(mipPestSurvey.getId())).collect(Collectors.toList()));
    //     data.addAttribute("success", this.operationSuccessMessage);

    //     this.resetOperationSuccessMessage();
        
    //     data.addAttribute("urlDelete", this.environment.getProperty("app.view.route.delete.mip.pest"));
        
    //     return this.environment.getProperty("app.view.route.mip.list.collected-samples");
    // }

    
}