package com.moviesquare.moviesquare.security;

import com.moviesquare.moviesquare.clients.UserAPI;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import lombok.RequiredArgsConstructor;
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    private final UserAPI userAPI;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
     
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
            .and()
            .authorizeRequests() 
                .antMatchers("/makegif").hasAnyRole("OLDBIE","NEWBIE")
                .antMatchers("/makestory").hasRole("OLDBIE")
          
                .antMatchers("/uploadgif").hasAnyRole("OLDBIE","NEWBIE")
                .antMatchers("/uploadStory").hasRole("OLDBIE")

            .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(userAPI)) 
                .addFilterAfter(new JwtTokenVerifier(userAPI), JwtUsernameAndPasswordAuthenticationFilter.class) 
                .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginErroHandler())
            ;
        http.exceptionHandling().accessDeniedPage("/main/accessdenied");
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/main/**")
            .antMatchers("/korea/**","/usa/**","/marvel/**","/usa/**","/japan/**")
            .antMatchers("/join/**")
            // .antMatchers("/uploadgif")
            .antMatchers("/quiz/**")
            .antMatchers("/loginhandler")
            .antMatchers("/logout")
            .antMatchers("/loginerror") 
            .antMatchers("/by/**","/tagadd","/tagtotal")
            .antMatchers("/design/**")
            .antMatchers("/favicon.ico")
            .antMatchers("/","index","/css/**","/js/*")
            .antMatchers("/fonts/**","/imgs/**","/jss/**")
            ;
    }
 



    
}
