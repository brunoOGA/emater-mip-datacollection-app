package br.edu.utfpr.cp.emater.midmipsystem.view.mid;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BladeReadingDTO {

    private String harvestName;
    private String fieldName;
    private String fieldLocation;
    private String fieldCityName;
    private String seedName;
    private String farmerName;
    private String supervisorNames[];    

    private BladeReadingDTO() {}
}