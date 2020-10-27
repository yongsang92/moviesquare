 
package com.moviesquare.japan.security;
import javax.crypto.SecretKey;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(new JwtTokenVerifier(secretKey,jwtConfig),UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()
            .antMatchers("/jp/**","/jptags/**","/getstories","/getstoriestotal"
            ,"/story/{id}","/relstories/{id}").permitAll()
            .anyRequest()
            .authenticated();
    }
    
}
