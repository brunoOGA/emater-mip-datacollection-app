package br.edu.utfpr.cp.emater.midmipsystem.library.dtos.base;

import java.io.Serializable;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MacroRegionDTO implements Serializable {

    private Long id;
    
    @Size (min = 3, max = 50, message = "O nome da macrorregião deve ter entre 3 e 50 caracteres")
    protected String name;
}
