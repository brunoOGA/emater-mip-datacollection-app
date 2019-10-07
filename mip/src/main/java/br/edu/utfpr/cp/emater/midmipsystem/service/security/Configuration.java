package br.edu.utfpr.cp.emater.midmipsystem.service.security;

import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Configuration extends WebSecurityConfigurerAdapter implements Serializable{
    
    private final MIPUserDetailsService mIPUserDetailsService;

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return mIPUserDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
//                auth
//                .inMemoryAuthentication()
//                .withUser("tecnico").password(passwordEncoder().encode("567")).roles("USER").and()
//                .withUser("apucarana").password(passwordEncoder().encode("apucarana")).roles("USER").and()
//                .withUser("campomourao").password(passwordEncoder().encode("campomourao")).roles("USER").and()
//                .withUser("cascavel").password(passwordEncoder().encode("cascavel")).roles("USER").and()
//                .withUser("cianorte").password(passwordEncoder().encode("cianorte")).roles("USER").and()
//                .withUser("cornelioprocopio").password(passwordEncoder().encode("cornelioprocopio")).roles("USER").and()
//                .withUser("curitiba").password(passwordEncoder().encode("curitiba")).roles("USER").and()
//                .withUser("doisvizinhos").password(passwordEncoder().encode("doisvizinhos")).roles("USER").and()
//                .withUser("franciscobeltrao").password(passwordEncoder().encode("fransciscobeltrao")).roles("USER").and()
//                .withUser("guarapuava").password(passwordEncoder().encode("guarapuava")).roles("USER").and()
//                .withUser("irati").password(passwordEncoder().encode("irati")).roles("USER").and()
//                .withUser("ivaipora").password(passwordEncoder().encode("ivaipora")).roles("USER").and()
//                .withUser("laranjeirasdosul").password(passwordEncoder().encode("laranjeirasdosul")).roles("USER").and()
//                .withUser("londrina").password(passwordEncoder().encode("londrina")).roles("USER").and()
//                .withUser("maringa").password(passwordEncoder().encode("maringa")).roles("USER").and()
//                .withUser("patobranco").password(passwordEncoder().encode("patobranco")).roles("USER").and()
//                .withUser("pontagrossa").password(passwordEncoder().encode("pontagrossa")).roles("USER").and()
//                .withUser("santoantoniodaplatina").password(passwordEncoder().encode("santoantoniodaplatina")).roles("USER").and()
//                .withUser("toledo").password(passwordEncoder().encode("toledo")).roles("USER").and()
//                .withUser("uniaodavitoria").password(passwordEncoder().encode("uniaodavitoria")).roles("USER");
    }

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
