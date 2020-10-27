package com.member.www.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import com.member.www.properties.JwtConfig;
import com.member.www.security.AuthenticationCustom;
import com.member.www.security.GrantedAuthorityCustom;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public String getJwt(AuthenticationCustom authResult){

        String token=Jwts.builder()
                        .setSubject(authResult.getName())
                        .claim("authorities",authResult.getAuthorities())
                        .setIssuedAt(new Date())
                        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(jwtConfig.getTokenExpirationAfterDays())))
                        .signWith(secretKey)
                        .compact();
        return token;
    }

    //TODO FIX : 단순히 이름을 반환하고 있는데 역할을 반환하도록 해야 할거? 같다. 근데 이름도 반환해줘야 하는디
    /* 
     * 전달받은 JWT 검증한다
     * 전달받은 JWT에서 authorities를 추출해서 리턴한다
     */
    public Set<GrantedAuthorityCustom> authenticateJwt(String jwt){
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt);
        Claims body = claimsJws.getBody();
        var authorities=(List<Map<String, String>>) body.get("authorities");


        Set<GrantedAuthorityCustom> result=
            authorities.stream()
            .map(element -> new GrantedAuthorityCustom(element.get("authority")))
            .collect(Collectors.toSet());

        return result;
    }
    
}
