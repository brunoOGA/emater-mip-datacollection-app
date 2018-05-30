/**
 * This file was generated by the Jeddict
 */
package br.edu.utfpr.cp.emater.mipdatacollection.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author gabriel
 */
@Entity
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    private String name;

    @ManyToOne(targetEntity = MacroRegion.class)
    private MacroRegion macroRegion;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MacroRegion getMacroRegion() {
        return this.macroRegion;
    }

    public void setMacroRegion(MacroRegion macroRegion) {
        this.macroRegion = macroRegion;
    }

}