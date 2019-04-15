package br.edu.utfpr.cp.emater.midmipsystem;


import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.MacroRegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.CityRepository;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.RegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.State;
import java.util.Locale;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
@EnableJpaAuditing
public class MipApplication {

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
    private RegionRepository regionRepository;
    private CityRepository cityRepository;
    
    @Autowired
    CLR (MacroRegionRepository macroRegionRepository, RegionRepository aRegionRepository, CityRepository aCityRepository) {
        this.macroRegionRepository = macroRegionRepository;
        this.regionRepository = aRegionRepository;
        this.cityRepository = aCityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        
        var mr1 = macroRegionRepository.save(MacroRegion.builder().name("Macro Noroeste").build());
        var mr2 = macroRegionRepository.save(MacroRegion.builder().name("Macro Norte").build());
        var mr3 = macroRegionRepository.save(MacroRegion.builder().name("Macro Oeste").build());
        var mr4 = macroRegionRepository.save(MacroRegion.builder().name("Macro Sul").build());
        
        var c1 = cityRepository.save(City.builder().name("Itapejara D'Oeste").state(State.PR).build());
        var c2 = cityRepository.save(City.builder().name("Mariópolis").state(State.PR).build());
        var c3 = cityRepository.save(City.builder().name("Pato Branco").state(State.PR).build());
        var c4 = cityRepository.save(City.builder().name("Apucarana").state(State.PR).build());
        var c5 = cityRepository.save(City.builder().name("Campo Mourão").state(State.PR).build());

//        var r1 = regionRepository.save(Region.builder().name("Apucarana").macroRegion(mr1).build());
//        var r2 = regionRepository.save(Region.builder().name("Campo Mourão").macroRegion(mr2).build());
//        var r3 = regionRepository.save(Region.builder().name("Cascavel").macroRegion(mr3).build());
//        var r4 = regionRepository.save(Region.builder().name("Cianorte").macroRegion(mr2).build());
//        var r5 = regionRepository.save(Region.builder().name("Cornélio Procópio").macroRegion(mr2).build());
//        var r6 = regionRepository.save(Region.builder().name("Curitiba").macroRegion(mr2).build());
        
        var region7 = Region.builder().name("Dois Vizinhos").macroRegion(mr4).build(); 
        region7.addCity(c1);
        var r7 = regionRepository.save(region7);
                
//        var r8 = regionRepository.save(Region.builder().name("Francisco Beltrão").macroRegion(mr4).build());
//        var r9 = regionRepository.save(Region.builder().name("Guarapuava").macroRegion(mr2).build());
//        var r10 = regionRepository.save(Region.builder().name("Irati").macroRegion(mr2).build());
//        var r11 = regionRepository.save(Region.builder().name("Ivaiporã").macroRegion(mr2).build());
//        var r12 = regionRepository.save(Region.builder().name("Laranjeiras do Sul").macroRegion(mr2).build());
//        var r13 = regionRepository.save(Region.builder().name("Londrina").macroRegion(mr2).build());
//        var r14 = regionRepository.save(Region.builder().name("Maringá").macroRegion(mr1).build());
//        var r15 = regionRepository.save(Region.builder().name("Paranaguá").macroRegion(mr2).build());
//        var r16 = regionRepository.save(Region.builder().name("Paranavaí").macroRegion(mr1).build());
        
        var region17 = Region.builder().name("Pato Branco").macroRegion(mr4).build(); 
        region17.addCity(c2);
        region17.addCity(c3);
        var r17 = regionRepository.save(region17);
        
//        var r18 = regionRepository.save(Region.builder().name("Ponta Grossa").macroRegion(mr2).build());
//        var r19 = regionRepository.save(Region.builder().name("Sto. Antonio da Platina").macroRegion(mr2).build());
//        var r20 = regionRepository.save(Region.builder().name("Toledo").macroRegion(mr2).build());
//        var r21 = regionRepository.save(Region.builder().name("Umuarama").macroRegion(mr1).build());
//        var r22 = regionRepository.save(Region.builder().name("União da Vitória").macroRegion(mr4).build());
        
        
    }

}
