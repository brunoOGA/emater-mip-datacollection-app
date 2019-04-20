package br.edu.utfpr.cp.emater.midmipsystem;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.MacroRegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.CityRepository;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.RegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.State;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.FarmerRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.FieldRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.SupervisorRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.HarvestRepository;
import java.time.LocalDate;
import java.time.Month;
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
    private FarmerRepository farmerRepository;
    private SupervisorRepository supervisorRepository;
    private FieldRepository fieldRepository;

    private HarvestRepository harvestRepository;

    @Autowired
    CLR(MacroRegionRepository macroRegionRepository,
            RegionRepository aRegionRepository,
            CityRepository aCityRepository,
            FarmerRepository aFarmerRepository,
            SupervisorRepository aSupervisorRepository,
            FieldRepository aFieldRepository,
            HarvestRepository aHarvestRepository) {

        this.macroRegionRepository = macroRegionRepository;
        this.regionRepository = aRegionRepository;
        this.cityRepository = aCityRepository;
        this.farmerRepository = aFarmerRepository;
        this.supervisorRepository = aSupervisorRepository;
        this.fieldRepository = aFieldRepository;

        this.harvestRepository = aHarvestRepository;
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
        var f1 = farmerRepository.save(Farmer.builder().name("Gilson Dariva").build());
        var f2 = farmerRepository.save(Farmer.builder().name("LUIZ ARCANGELO GIORDANI").build());
        var f3 = farmerRepository.save(Farmer.builder().name("Maurílio Bertoldo").build());
        var f4 = farmerRepository.save(Farmer.builder().name("Rafael Oldoni").build());
        var f5 = farmerRepository.save(Farmer.builder().name("Clemente Carnieletto").build());

        var s1 = supervisorRepository.save(Supervisor.builder().name("Lari Maroli").email("maroli@emater.pr.gov.br").region(r7).build());
        var s2 = supervisorRepository.save(Supervisor.builder().name("IVANDERSON BORELLI").email("borelli@emater.pr.gov.br").region(r17).build());
        var s3 = supervisorRepository.save(Supervisor.builder().name("José Francisco Vilas Boas").email("villas@emater.pr.gov.br").region(r17).build());
        var s4 = supervisorRepository.save(Supervisor.builder().name("Vilmar Grando").email("grando@emater.pr.gov.br").region(r17).build());

        var field1 = Field.builder().name("Trevo").location("").city(c1).farmer(f1).build();
        field1.addSupervisor(s1);
        var persistentField1 = fieldRepository.save(field1);

        var field2 = Field.builder().name("Carnieletto").location("").city(c3).farmer(f5).build();
        field2.addSupervisor(s4);
        var persistentField2 = fieldRepository.save(field2);

        var field3 = Field.builder().name("Oldoni").location("").city(c3).farmer(f4).build();
        field3.addSupervisor(s4);
        var persistentField3 = fieldRepository.save(field3);

        var field4 = Field.builder().name("MIP e MID").location("").city(c2).farmer(f2).build();
        field4.addSupervisor(s2);
        var persistentField4 = fieldRepository.save(field4);

        var field5 = Field.builder().name("Bertoldo").location("1").city(c3).farmer(f3).build();
        field5.addSupervisor(s3);
        var persistentField5 = fieldRepository.save(field5);

        var harvest1 = harvestRepository.save(
                Harvest.builder()
                        .name("Safra 2016/2017")
                        .begin(LocalDate.of(2016, Month.OCTOBER, 1))
                        .end(LocalDate.of(2017, Month.MARCH, 1))
                        .build()
        );        
        
        var harvest2 = harvestRepository.save(
                Harvest.builder()
                        .name("Safra 2017/2018")
                        .begin(LocalDate.of(2017, Month.OCTOBER, 1))
                        .end(LocalDate.of(2018, Month.MARCH, 1))
                        .build()
        );
    }

}
