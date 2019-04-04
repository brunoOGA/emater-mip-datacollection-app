package br.edu.utfpr.cp.emater.midmipsystem.view.mid;

public class BladeReadingSampleDTO {

    public enum AsiaticRustTypes {
        NO_RUST_SPORES ("1 - Sem Esporos de Ferrugem"),
        NO_RUST_SPORES_NO_FEASIBILITY_TEST ("2 - Com Esporos - Mas, sem testar viabilidade"),
        UNFEASIBLE_SPORES_AFTER_TEST ("3 - Com Esporos - Mas, inviáveis após teste"),
        FEASIBLE_SPORES_ISOLATED ("4 - Com Esporos Viáveis - Isolados"),
        FEASIBLE_SPORES_GROUPED ("5 - Com Esporos Viáveis - Aglomerados");

        private String description;

        AsiaticRustTypes (String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    
}