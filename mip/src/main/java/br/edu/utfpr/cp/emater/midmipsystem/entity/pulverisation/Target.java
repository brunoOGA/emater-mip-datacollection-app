package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.AuditingPersistenceEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.apache.commons.text.WordUtils;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Target extends AuditingPersistenceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @EqualsAndHashCode.Include
    @Size(min = 3, max = 80, message = "A descrição deve ter entre 3 e 80 caracteres")
    protected String description;

    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    private UseClass useClass;

    public void description(String usualName) {
        this.description = WordUtils.capitalize(usualName.toLowerCase());
    }

    @Builder
    public static Target create(Long id, String description, UseClass useClass) {
        Target instance = new Target();
        instance.setId(id);
        instance.setDescription(description);
        instance.setUseClass(useClass);

        return instance;
    }

    public boolean isInseticidaUseClass() {

        if (this.useClass.equals(UseClass.INSETICIDA)) {
            return true;

        } else {
            return false;
        }
    }

    public boolean isInseticidaBiologicoUseClass() {

        if (this.useClass.equals(UseClass.INCETICIDA_BIOLOGICO)) {
            return true;

        } else {
            return false;
        }
    }

    public boolean isFungicidaUseClass() {

        if (this.useClass.equals(UseClass.FUNGICIDA)) {
            return true;

        } else {
            return false;
        }
    }

    public boolean isAcaricidaUseClass() {

        if (this.useClass.equals(UseClass.ACARICIDA)) {
            return true;
        } else {
            return false;
        }
    }

}
