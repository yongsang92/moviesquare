package com.moviesquare.moviesquare.security;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class AuthenticationCustom implements Authentication {
    
    private Collection<GrantedAuthorityCustom> authorities;
    private boolean authenticated;

	private Object principal;
    private Object credentials;
    
 
    public AuthenticationCustom(Authentication authentication){
        
        
        this.authorities=
            authentication.getAuthorities()
                .stream()
                .map(element->new GrantedAuthorityCustom(element.getAuthority()))
                .collect(Collectors.toList());

        this.principal = authentication.getPrincipal();
        this.credentials = authentication.getCredentials();
        this.authenticated=authentication.isAuthenticated();
    }

    @Override
    public String getName() {
        return principal.toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authenticated=true;
    }

    
    
}
