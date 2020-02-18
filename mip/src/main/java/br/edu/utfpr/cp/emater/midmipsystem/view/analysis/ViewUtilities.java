package br.edu.utfpr.cp.emater.midmipsystem.view.analysis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;


@Component
@Getter
@NoArgsConstructor
@RequestScope
public class ViewUtilities {
    
    @Value ("${mip.app.current-version}")
    private String currentVersion;
    
}
