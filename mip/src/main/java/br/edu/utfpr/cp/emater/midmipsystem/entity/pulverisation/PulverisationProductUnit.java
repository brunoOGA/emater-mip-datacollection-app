package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

public enum PulverisationProductUnit {

    
    L("L"),
    ML("ml"),
    KG("kg"),
    g("g");

    private String description;

    PulverisationProductUnit(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
