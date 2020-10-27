package com.member.www.role;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.google.common.collect.Sets;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor
@Getter
public enum Role {

    NEWBIE(Sets.newHashSet(Authority.GIF_READ, Authority.STORY_READ, Authority.POST_GIF)),
    OLDBIE(Sets.newHashSet(Authority.GIF_READ, Authority.STORY_READ, Authority.POST_GIF, Authority.POST_STORY));
    
    private final Set<Authority> authorities;

    /* 
    
    */
    public Set<GrantedAuthority> getGrantedAuthorities() {
        Set<GrantedAuthority> authorities = getAuthorities().stream()
                .map(Authority -> new SimpleGrantedAuthority(Authority.name()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
