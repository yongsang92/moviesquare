package com.moviesquare.member.security;
import java.util.Set;

import com.moviesquare.member.model.Member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApplicationMember implements UserDetails {

    private String username, password;
    private Set<? extends GrantedAuthority> authorities;
    private final boolean isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled;

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public ApplicationMember(Member member) {
        this.username = member.getName();
        this.password = member.getPw();
        this.authorities = member.getRole().getGrantedAuthorities();
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
    }

}
