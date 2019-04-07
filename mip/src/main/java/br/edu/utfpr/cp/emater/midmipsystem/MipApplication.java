package br.edu.utfpr.cp.emater.midmipsystem;


import br.edu.utfpr.cp.emater.midmipsystem.domain.base.macroRegion.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.domain.base.macroRegion.MacroRegionRepository;
import java.util.Locale;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@SpringBootApplication
@EnableJpaAuditing
public class MipApplication {

    private FreeMarkerConfigurer freeMarkerConfigurer;
    

    @Autowired
    public MipApplication(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
        
        this.freeMarkerConfigurer.getConfiguration().addAutoImport("spring", "spring.ftl");
    }

    public static void main(String[] args) {
        SpringApplication.run(MipApplication.class, args);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("pt", "BR"));
        return slr;
    }
}

@Component
class CLR implements CommandLineRunner {
    
    private MacroRegionRepository macroRegionRepository;
    
    @Autowired
    CLR (MacroRegionRepository macroRegionRepository) {
        this.macroRegionRepository = macroRegionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        
        var mr1 = macroRegionRepository.save(MacroRegion.builder().name("Macro Noroeste").build());
        var mr2 = macroRegionRepository.save(MacroRegion.builder().name("Macro Norte").build());
        var mr3 = macroRegionRepository.save(MacroRegion.builder().name("Macro Oeste").build());
        var mr4 = macroRegionRepository.save(MacroRegion.builder().name("Macro Sul").build());
        
    }

}

@Controller
class App {
    
    @RequestMapping (value = "/", method = RequestMethod.GET)
    public String init () {
        return "index";
    }
}
