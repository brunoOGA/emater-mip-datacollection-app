package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Supervisor extends Person implements Serializable {

    private String email;

    @ManyToOne
    private Region region;
    
    public Supervisor () {
        super();
    }

    public Supervisor (Long id, String name, String email, Region region) {
        this();

        this.setId (id);
        this.setName (name);
        this.setEmail (email);
        this.setRegion(region);
    }
    
}
