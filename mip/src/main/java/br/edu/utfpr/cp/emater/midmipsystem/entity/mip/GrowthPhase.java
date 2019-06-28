package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

public enum GrowthPhase {

    
    V1("V1"),
    V2("V2"),
    V3("V3"),
    V5("V5"),
    V6("V6"),
    R1("R1"),
    R2("R2"),
    R3("R3"),
    R4("R4"),
    R4_R5("R4/R5"),
    R5_1("R5.1"),
    R5_2("R5.2"),
    R5_3("R5.3"),
    R5_4("R5.4"),
    R5_5_R6("R5.5/R6");

    private String description;

    GrowthPhase(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
