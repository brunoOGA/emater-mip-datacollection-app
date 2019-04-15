package br.edu.utfpr.cp.emater.midmipsystem.library.dtos.base;

import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionDTO implements Serializable {
    
    private Long id;
    
    @Size (min = 5, max = 50, message = "O nome da região deve ter entre 5 e 50 caracteres")
    private String name;
    
    @NotNull (message = "Deve ser informada uma macrorregião")
    private MacroRegionDTO macroRegion;
    
    @NotNull (message = "Deve ser informada pelo menos uma cidade que compoe a região")
    private Set<CityDTO> cities;
}
