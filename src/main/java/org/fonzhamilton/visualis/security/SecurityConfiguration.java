package org.fonzhamilton.visualis.security;

import lombok.extern.slf4j.Slf4j;
import org.fonzhamilton.visualis.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * SecurityConfiguration class completely refactored
 * IMPORTANT:
 * if you are going to use for specific endpoint more than one user role
 * always use hasAnyRole(...)
 * In order to be customizable always with Thymeleaf successForwardUrl(...) method */
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration {

    @Autowired
    private UserServiceImpl userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt

        return auth;
    }
    //beans
    //bcrypt bean definition
    @Bean
    @Scope("singleton")
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/login*",
                                "/css/*", "/js/*", "/sign-up", "/sign-up-processing","/about" ,
                                "/home","/storage/**").permitAll()
                        .requestMatchers("/visualize").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login") // should point to login page
                        .successForwardUrl("/visualize")
                        .permitAll()
                )

                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll()
                );

        return http.build();    // ma lemme build!
    }
}
