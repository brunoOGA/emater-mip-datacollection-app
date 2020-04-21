package br.edu.utfpr.cp.emater.midmipsystem.service.security;

import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Configuration extends WebSecurityConfigurerAdapter implements Serializable {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // form login
        http.authorizeRequests().antMatchers("/", "/login.xhtml", "/javax.faces.resource/**").permitAll().anyRequest()
                .fullyAuthenticated().and().formLogin().defaultSuccessUrl("/index.xhtml")
                .permitAll().and().logout()
                .logoutUrl("/j_spring_security_logout").and().csrf().disable();

        // allow to use ressource links like pdf
        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
