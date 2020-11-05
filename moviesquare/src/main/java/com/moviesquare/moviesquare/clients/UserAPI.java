package com.moviesquare.moviesquare.clients;
import java.util.Set;

import com.moviesquare.moviesquare.security.AuthenticationCustom;
import com.moviesquare.moviesquare.security.AuthenticationRequest;
import com.moviesquare.moviesquare.security.GrantedAuthorityCustom;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("memberservice")
public interface UserAPI {

    /* 로그인에 성공하면 JWT를 반환받는다 */
    @GetMapping(value = "member/login")
    String login(@RequestParam(name="name")String name,@RequestParam(name="pw")String pw);
    
    @GetMapping(value = "member/getName")
    String getName(@RequestParam(name="jwt")String jwt);
    
    @PostMapping(value = "member/join")
    boolean join(@RequestParam(name="name")String name,@RequestParam(name="pw")String pw);

    @PostMapping(value="member/authenticate")
    AuthenticationCustom authenticate(@RequestBody AuthenticationRequest aunthenticationRequest);

    @PostMapping(value="member/jwt")
	String getJwt(@RequestBody AuthenticationCustom authResult);
    
    @PostMapping(value="member/authenticate/jwt")
	Set<GrantedAuthorityCustom> parseToken(@RequestParam(name="jwt")String jwt);
    
}
