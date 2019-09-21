package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PulverisationData implements Serializable {

    private double soyaPrice;
    private double applicationCostCurrency;
    private double applicationCostQty;
    
    private double calculateApplicationCostQty (double aSoyaPrice, double anApplicationCostCurrency) {
        return anApplicationCostCurrency / aSoyaPrice;
    }
    
    public void setApplicationCostCurrency (double anApplicationCostCurrency) {
        this.applicationCostCurrency = anApplicationCostCurrency;
        
        if (soyaPrice != 0)
            this.setApplicationCostQty(this.calculateApplicationCostQty(this.getSoyaPrice(), this.getApplicationCostCurrency()));
    }
    
    public void setSoyaPrice (double aSoyaPrice) {
        this.soyaPrice = aSoyaPrice;
        
        if (applicationCostCurrency != 0)
            this.setApplicationCostQty(this.calculateApplicationCostQty(this.getSoyaPrice(), this.getApplicationCostCurrency()));
    }
}
