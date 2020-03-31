package br.edu.utfpr.cp.emater.midmipsystem.service.security;

import br.edu.utfpr.cp.emater.midmipsystem.entity.security.MIPUserPrincipal;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.SupervisorService;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
@Log
public class LoginListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {
    
    private final SupervisorService supervisorSerice;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session= attr.getRequest().getSession(true);
        
        var currentUser = ((MIPUserPrincipal)event.getAuthentication().getPrincipal()).getUser();
        session.setAttribute("currentUser", currentUser);
            
        session.setAttribute("currentSupervisor", supervisorSerice.readByEmail(currentUser.getEmail()).get());
    }    
}
