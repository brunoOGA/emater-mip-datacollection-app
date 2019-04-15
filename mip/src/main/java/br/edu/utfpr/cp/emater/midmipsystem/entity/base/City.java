package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.text.WordUtils;


@Entity
@Getter (AccessLevel.PUBLIC)
@Setter (AccessLevel.PACKAGE)
@EqualsAndHashCode (onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City extends AuditingPersistenceEntity implements Serializable {
    
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @EqualsAndHashCode.Include
    private String name;
    
    @Enumerated (EnumType.STRING)
    @EqualsAndHashCode.Include
    private State state;
        
    void setName (String name) {
        this.name = WordUtils.capitalize(name.toLowerCase());
    }
}