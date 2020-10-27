package com.moviesquare.member.security;

import org.springframework.security.core.GrantedAuthority;

import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@ToString
public class GrantedAuthorityCustom  implements GrantedAuthority{

    private String authority;

    public GrantedAuthorityCustom( String authority){
        this.authority=authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    
}
