package br.edu.utfpr.cp.emater.midmipsystem.view.mid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RustMonitoringSampleDTO {

    public enum AsiaticRustTypesSporeCollector {
        NO_RUST_SPORES ("1 - Sem Esporos de Ferrugem"),
        NO_RUST_SPORES_NO_FEASIBILITY_TEST ("2 - Com Esporos - Mas, sem testar viabilidade"),
        UNFEASIBLE_SPORES_AFTER_TEST ("3 - Com Esporos - Mas, inviáveis após teste"),
        FEASIBLE_SPORES_ISOLATED ("4 - Com Esporos Viáveis - Isolados"),
        FEASIBLE_SPORES_GROUPED ("5 - Com Esporos Viáveis - Aglomerados");

        private String description;

        AsiaticRustTypesSporeCollector (String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public enum GrowthPhase {
    
        V1 ("V1"),
        V5 ("V5"),
        R2 ("R2"),
        R3 ("R3"),
        R4_R5 ("R4/R5"),
        R5_1 ("R5.1"),
        R5_2 ("R5.2"),
        R5_3 ("R5.3"),
        R5_4 ("R5.4"),
        R5_5_R6 ("R5.5/R6");

        private String description;

        GrowthPhase (String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public enum AsiaticRustTypesInspection {
        NO_VISIBLE_DAMAGE ("1 - Sem Lesões Visíveis"),
        VISIBLE_DAMAGE_ISOLATED ("2 - Com Lesões Visíveis - em Plantas Isoladas"),
        VISIBLE_DAMAGE_PARTIAL_CROP ("3 - Com Lesões Visíveis - em Parte da Lavoura"),
        VISIBLE_DAMAGE_ALL_CROP ("4 - Com Lesões Visíveis - em Toda a Lavoura"),
        NO_RUST_LIKELY_OTHER_DISESES ("5 - Sem Ferrugem, mas c/ sinais de Outras Doenças");

        private String description;

        AsiaticRustTypesInspection (String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }    

    private int surveyFieldId;
    private String collectorInstallDate;
    private String colletionDate;
    private String readingBladeResponsibleName;
    private String readingBladeResponsibleNameEntity;
    private String bladeReadingDate;
    private String bladeReadingRustResultCollector;
    private boolean bladeInstalledPreCold;
    private String growthPhase;
    private String bladeReadingRustResultInspection;
    private boolean asiaticRustApplication;
    private boolean otherDiseasesApplication;
    private String fungicideApplicationDate;
    private String fungicideNotes;

    @Override
    public String toString() {
        return String.format("surveyFieldId = %d " +
                            "collectorInstallDate = %s" +
                            "colletionDate = %s" +
                            "readingBladeResponsibleName = %s" +
                            "readingBladeResponsibleNameEntity = %s" +
                            "bladeReadingDate = %s" +
                            "bladeReadingRustResultCollector = %s" +
                            "bladeInstalledPreCold = %b" +
                            "growthPhase = %s" +
                            "bladeReadingRustResultInspection = %s" +
                            "asiaticRustApplication = %b" +
                            "otherDiseasesApplication = %b" +
                            "fungicideApplicationDate = %s" +
                            "fungicideNotes = %s", 
                            this.surveyFieldId,
                            this.collectorInstallDate,
                            this.colletionDate,
                            this.readingBladeResponsibleName,
                            this.readingBladeResponsibleNameEntity,
                            this.bladeReadingDate,
                            this.bladeReadingRustResultCollector,
                            this.bladeInstalledPreCold,
                            this.growthPhase,
                            this.bladeReadingRustResultInspection,
                            this.asiaticRustApplication,
                            this.otherDiseasesApplication,
                            this.fungicideApplicationDate,
                            this.fungicideNotes);
    }
}