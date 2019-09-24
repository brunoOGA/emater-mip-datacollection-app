package br.edu.utfpr.cp.emater.midmipsystem.security;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Named
@ViewScoped
public class SessionMB implements Serializable {

    public String getCurrentUser() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}