package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class PulverisationData implements Serializable {

    @Positive(message = "O preço da soja precisa ser um valor maior que zero.")
    private double soyaPrice;

    @Positive(message = "O custo da aplicação precisa ser um valor maior que zero.")
    private double applicationCostCurrency;
    private double applicationCostQty;

    @Builder
    public static PulverisationData create(double soyaPrice, double applicationCostCurrency) {
        var instance = new PulverisationData();
        instance.setSoyaPrice(soyaPrice);
        instance.setApplicationCostCurrency(applicationCostCurrency);

        return instance;
    }

    private double calculateApplicationCostQty(double aSoyaPrice, double anApplicationCostCurrency) {
        return anApplicationCostCurrency / aSoyaPrice;
    }

    public void setApplicationCostCurrency(double anApplicationCostCurrency) {
        this.applicationCostCurrency = anApplicationCostCurrency;

        if (soyaPrice != 0) {
            this.setApplicationCostQty(this.calculateApplicationCostQty(this.getSoyaPrice(), this.getApplicationCostCurrency()));
        }
    }

    public void setSoyaPrice(double aSoyaPrice) {
        this.soyaPrice = aSoyaPrice;

        if (applicationCostCurrency != 0) {
            this.setApplicationCostQty(this.calculateApplicationCostQty(this.getSoyaPrice(), this.getApplicationCostCurrency()));
        }
    }
}
