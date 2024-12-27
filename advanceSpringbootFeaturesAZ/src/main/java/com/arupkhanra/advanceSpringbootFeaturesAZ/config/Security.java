package com.arupkhanra.advanceSpringbootFeaturesAZ.config;



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

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;

    /**
     * Configures HTTP security, setting up basic authentication and authorization rules.
     *
     * @param http the HttpSecurity object to configure.
     * @throws Exception in case of any configuration errors.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Authorization rules
                .authorizeRequests()
                .antMatchers("/journal/**", "/user/**").authenticated() // Secure these endpoints
                .anyRequest().permitAll() // Allow all other endpoints
                .and()
                // Enable HTTP Basic authentication
                .httpBasic()
                .and()
                // Disable CSRF protection and enforce stateless sessions
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
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
