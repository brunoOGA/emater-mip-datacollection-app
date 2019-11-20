package br.edu.utfpr.cp.emater.midmipsystem.entity.analysis;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DAEAndOccurrence {
    
    private int dae;
    private double occurrence;
    
    @Builder
    public static DAEAndOccurrence create (int dae, double occurrence) {
        var result = new DAEAndOccurrence();
        
        result.dae = dae;
        result.occurrence = occurrence;
        
        return result;
    }
    
}
