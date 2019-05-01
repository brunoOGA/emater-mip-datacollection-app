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
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestSize;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.FarmerRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.FieldRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.SupervisorRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.HarvestRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.SurveyRepository;
import java.text.SimpleDateFormat;
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
    private SurveyRepository surveyRepository;
    
    private PestRepository pestRepository;

    @Autowired
    CLR(MacroRegionRepository macroRegionRepository,
            RegionRepository aRegionRepository,
            CityRepository aCityRepository,
            FarmerRepository aFarmerRepository,
            SupervisorRepository aSupervisorRepository,
            FieldRepository aFieldRepository,
            HarvestRepository aHarvestRepository,
            SurveyRepository aSurveyRepository,
            PestRepository aPestRepository) {

        this.macroRegionRepository = macroRegionRepository;
        this.regionRepository = aRegionRepository;
        this.cityRepository = aCityRepository;
        this.farmerRepository = aFarmerRepository;
        this.supervisorRepository = aSupervisorRepository;
        this.fieldRepository = aFieldRepository;

        this.harvestRepository = aHarvestRepository;
        this.surveyRepository = aSurveyRepository;
        
        this.pestRepository = aPestRepository;
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
                        .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2016"))
                        .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-2017"))
                        .build()
        );        
        
        var harvest2 = harvestRepository.save(
                Harvest.builder()
                        .name("Safra 2017/2018")
                        .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2017"))
                        .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-2018"))
                        .build()
        );
        
        var survey1 = surveyRepository.save(
            Survey.builder()
                    .harvest(harvest1)
                    .field(persistentField3)
                    .seedName("TMG 7262 RR1")
                    .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-1"))
                    .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-8"))
                    .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-02-26"))
                    .rustResistant(true)
                    .bt(false)
                    .totalArea(4.4)
                    .totalPlantedArea(10)
                    .plantPerMeter(9)
                    .productivityField(161.7)
                    .productivityFarmer(159.5)
                    .separatedWeight(true)
                    .longitude(2.5)
                    .latitude(3.5)
                    .build()
        );
        
        var survey2 = surveyRepository.save(
            Survey.builder()
                    .harvest(harvest1)
                    .field(persistentField2)
                    .seedName("BMX RAIO Ipro")
                    .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-4"))
                    .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-11"))
                    .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-2-12"))
                    .rustResistant(false)
                    .bt(true)
                    .totalArea(18)
                    .totalPlantedArea(62)
                    .plantPerMeter(13)
                    .productivityField(197)
                    .productivityFarmer(182)
                    .separatedWeight(true)
                    .longitude(3.5)
                    .latitude(4.5)
                    .build()
        );
        

        var survey3 = surveyRepository.save(
            Survey.builder()
                    .harvest(harvest1)
                    .field(persistentField5)
                    .seedName("TMG 7262 RR")
                    .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-4"))
                    .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-9"))
                    .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-2-20"))
                    .rustResistant(true)
                    .bt(false)
                    .totalArea(5.74)
                    .totalPlantedArea(35.09)
                    .plantPerMeter(11)
                    .productivityField(137.5)
                    .productivityFarmer(120)
                    .separatedWeight(true)
                    .longitude(4.5)
                    .latitude(5.5)
                    .build()
        );
        
        var survey4 = surveyRepository.save(
            Survey.builder()
                    .harvest(harvest1)
                    .field(persistentField4)
                    .seedName("TMG -  7262")
                    .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-24"))
                    .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-10"))
                    .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-2-18"))
                    .rustResistant(true)
                    .bt(false)
                    .totalArea(3.63)
                    .totalPlantedArea(3.63)
                    .plantPerMeter(9)
                    .productivityField(158.5)
                    .productivityFarmer(158.5)
                    .separatedWeight(true)
                    .longitude(6.5)
                    .latitude(6.5)
                    .build()
        );        
        
        var survey5 = surveyRepository.save(
            Survey.builder()
                    .harvest(harvest1)
                    .field(persistentField1)
                    .seedName("P95R51")
                    .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-9-26"))
                    .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-10"))
                    .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-2-15"))
                    .rustResistant(false)
                    .bt(false)
                    .totalArea(7.26)
                    .totalPlantedArea(242)
                    .plantPerMeter(15)
                    .productivityField(187)
                    .productivityFarmer(170)
                    .separatedWeight(true)
                    .longitude(7.5)
                    .latitude(8.5)
                    .build()
        ); 
        
        Pest p1 = pestRepository.save(Pest.builder().usualName("Lagarta da soja").scientificName("Anticarsia gemmatalis").pestSize(PestSize.GREATER_15CM).build());
        Pest p2 = pestRepository.save(Pest.builder().usualName("Lagarta da soja").scientificName("Anticarsia gemmatalis").pestSize(PestSize.SMALLER_15CM).build());
        Pest p3 = pestRepository.save(Pest.builder().usualName("Falsa medideira").scientificName("Chrysodeixis spp.").pestSize(PestSize.GREATER_15CM).build());
        Pest p4 = pestRepository.save(Pest.builder().usualName("Falsa medideira").scientificName("Chrysodeixis spp.").pestSize(PestSize.SMALLER_15CM).build());
        Pest p5 = pestRepository.save(Pest.builder().usualName("Lagarta das vagens").scientificName("Spodoptera spp.").pestSize(PestSize.GREATER_15CM).build());
        Pest p6 = pestRepository.save(Pest.builder().usualName("Lagarta das vagens").scientificName("Spodoptera spp.").pestSize(PestSize.SMALLER_15CM).build());
        Pest p7 = pestRepository.save(Pest.builder().usualName("Grupo Heliothinae").scientificName("").pestSize(PestSize.GREATER_15CM).build());
        Pest p8 = pestRepository.save(Pest.builder().usualName("Grupo Heliothinae").scientificName("Chrysodeixis spp.").pestSize(PestSize.SMALLER_15CM).build());
        Pest p9 = pestRepository.save(Pest.builder().usualName("Percevejo verde").scientificName("Nezara sp.").pestSize(PestSize.THIRD_TO_FIFTH_INSTAR).build());
        Pest p10 = pestRepository.save(Pest.builder().usualName("Percevejo verde").scientificName("Nezara sp.").pestSize(PestSize.ADULT).build());
        Pest p11 = pestRepository.save(Pest.builder().usualName("Percevejo verde pequeno").scientificName("Piezodorus sp.").pestSize(PestSize.THIRD_TO_FIFTH_INSTAR).build());
        Pest p12 = pestRepository.save(Pest.builder().usualName("Percevejo verde pequeno").scientificName("Piezodorus sp.").pestSize(PestSize.ADULT).build());
        Pest p13 = pestRepository.save(Pest.builder().usualName("Percevejo Marrom").scientificName("Eushistus sp.").pestSize(PestSize.THIRD_TO_FIFTH_INSTAR).build());
        Pest p14 = pestRepository.save(Pest.builder().usualName("Percevejo Marrom").scientificName("Eushistus sp.").pestSize(PestSize.ADULT).build());
        Pest p15 = pestRepository.save(Pest.builder().usualName("Percevejo Barriga verde").scientificName("Dichelops ssp.").pestSize(PestSize.THIRD_TO_FIFTH_INSTAR).build());
        Pest p16 = pestRepository.save(Pest.builder().usualName("Percevejo Barriga verde").scientificName("Dichelops ssp.").pestSize(PestSize.ADULT).build());
        Pest p17 = pestRepository.save(Pest.builder().usualName("Outros percevejos").scientificName("").pestSize(PestSize.THIRD_TO_FIFTH_INSTAR).build());
        Pest p18 = pestRepository.save(Pest.builder().usualName("Outros percevejos").scientificName("").pestSize(PestSize.ADULT).build());
    }

}
