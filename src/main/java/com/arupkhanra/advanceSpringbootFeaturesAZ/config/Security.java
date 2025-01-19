package com.arupkhanra.advanceSpringbootFeaturesAZ.config;

import com.arupkhanra.advanceSpringbootFeaturesAZ.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    /**
     * Configures HTTP security, setting up basic authentication and authorization rules.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Authorization rules
                .authorizeRequests()
                .antMatchers("/journal/**", "/user/**").authenticated() // Secure these endpoints
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                // Enable HTTP Basic authentication
               // .httpBasic()
                //.and()
                // Disable CSRF protection and enforce stateless sessions
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Configures authentication, linking to the custom user details service.
     *
     * @param auth the AuthenticationManagerBuilder object.
     * @throws Exception in case of configuration errors.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Defines a PasswordEncoder bean for encrypting and validating passwords.
     *
     * @return a BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
