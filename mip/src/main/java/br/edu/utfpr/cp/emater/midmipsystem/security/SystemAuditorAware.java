package br.edu.utfpr.cp.emater.midmipsystem.security;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SystemAuditorAware implements AuditorAware<String> {

  public Optional<String> getCurrentAuditor() {

     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return Optional.empty();
    }

    return Optional.of(((UserDetails) authentication.getPrincipal()).getUsername());
  }
}
