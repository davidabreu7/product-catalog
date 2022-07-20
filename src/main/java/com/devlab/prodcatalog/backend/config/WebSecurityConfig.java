package com.devlab.prodcatalog.backend.config;

import com.devlab.prodcatalog.backend.security.JwtTokenVerifierFilter;
import com.devlab.prodcatalog.backend.security.JwtUserPasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtConfig jwtConfig;

    public WebSecurityConfig(BCryptPasswordEncoder passwordEncoder, UserDetailsService userDetails, JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetails;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtUserPasswordAuthenticationFilter JwtUserPasswordAuthenticationFilter() throws Exception {
        var filter = new JwtUserPasswordAuthenticationFilter(jwtConfig);
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setFilterProcessesUrl("/login");

        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(JwtUserPasswordAuthenticationFilter())
                .addFilterAfter(new JwtTokenVerifierFilter(jwtConfig), JwtUserPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.GET, "/users")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated();

    }

}