package br.edu.utfpr.cp.emater.midmipsystem.domain.base.region;

public enum State {

    PR ("Paraná"),
    SC ("Santa Catarina"),
    RS ("Rio Grande do Sul");
    
    private String name;
    
    State (String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName (String name) {
        this.name = name;
    }
    
}