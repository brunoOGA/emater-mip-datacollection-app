package br.edu.utfpr.cp.emater.midmipsystem.domain.mid;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class RustMonitoringSampleOccurrence implements Serializable {

    private String name;

    
}