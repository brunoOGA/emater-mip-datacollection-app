package br.edu.utfpr.cp.emater.midmipsystem.view.base.macroRegion;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class MacroRegionDTO implements Serializable {
    
    private int id;
    private String name;
    
}
