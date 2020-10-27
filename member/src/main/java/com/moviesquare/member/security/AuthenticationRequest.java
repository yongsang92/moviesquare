package com.member.www.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
public class AuthenticationRequest {

    private String name,pw;
    
}
