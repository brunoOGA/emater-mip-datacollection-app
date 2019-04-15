package br.edu.utfpr.cp.emater.midmipsystem.library.dtos.base;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO implements Serializable {
    
    private Long id;
    private String name;
    private String state;
}