package br.edu.utfpr.cp.emater.midmipsystem.view.analysis;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@ViewScoped
@Component ("urDashboardController")
public class URDashboardController implements Serializable {
    
    @Getter
    @Setter
    private String message;
    
    @PostConstruct
    public void init() {
        message = "Hello World!";
    }
    
}
