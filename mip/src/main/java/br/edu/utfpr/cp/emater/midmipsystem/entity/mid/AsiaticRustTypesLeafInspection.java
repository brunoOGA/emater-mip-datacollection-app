package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

public enum AsiaticRustTypesLeafInspection {
    NO_VISIBLE_DAMAGE("Sem Lesões Visíveis"),
    VISIBLE_DAMAGE_ISOLATED("Com Lesões Visíveis - em Plantas Isoladas"),
    VISIBLE_DAMAGE_PARTIAL_CROP("Com Lesões Visíveis - em Parte da Lavoura"),
    VISIBLE_DAMAGE_ALL_CROP("Com Lesões Visíveis - em Toda a Lavoura"),
    NO_RUST_LIKELY_OTHER_DISESES("Sem Ferrugem, mas c/ sinais de Outras Doenças");

    private String description;

    AsiaticRustTypesLeafInspection(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
