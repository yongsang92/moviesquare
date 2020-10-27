package com.moviesquare.member.properties;
import com.google.common.net.HttpHeaders;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@PropertySource("classpath:jwt_token.yml")
@ConfigurationProperties(prefix="application.jwt")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component 
public class JwtConfig {

    private String secretKey;
    private Integer tokenExpirationAfterDays;
    
    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}

