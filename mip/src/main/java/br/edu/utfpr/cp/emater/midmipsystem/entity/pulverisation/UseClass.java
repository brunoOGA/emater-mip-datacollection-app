package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

public enum UseClass {

    
    HERBICIDA("Herbicida"),
    INSETICIDA("Inseticida"),
    INCETICIDA_BIOLOGICO("Inseticida Biológico"),
    AGENTE_BIOLOGICO("Agente Biológico"),
    FUNGICIDA("Fungicida"),
    ADJUVANTE("Adjuvante"),
    ADUBO_FOLIAR("Adubo Foliar"),
    OUTROS("Outros"),
    SAL_COMUM("Sal Comum");

    private String description;

    UseClass(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
