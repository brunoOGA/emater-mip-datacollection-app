package br.edu.utfpr.cp.emater.midmipsystem.view.base;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MacroRegionDTO implements Serializable {
    
    private Long id;
    private String name;
}
