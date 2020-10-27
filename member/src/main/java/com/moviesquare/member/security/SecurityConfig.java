package com.member.www.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final MemberUserDetailService memberUserDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 무상태로 설정 
            .and()
            .authorizeRequests()
            .antMatchers("/member/**").permitAll();
        
    }


    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	

    @Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(){
	  DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	  provider.setPasswordEncoder(passwordEncoder);
	  provider.setUserDetailsService(memberUserDetailService);
	  return provider;
    }
 
 
}
