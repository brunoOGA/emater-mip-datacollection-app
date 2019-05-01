package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

public enum PestSize {
    
    GREATER_15CM ("> 15 cm"),
    SMALLER_15CM ("< 15 cm"),
    THIRD_TO_FIFTH_INSTAR ("3. ao 5. Instar"),
    ADULT ("Adultos");
    
    private String name;
    
    PestSize (String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName (String name) {
        this.name = name;
    }
}
