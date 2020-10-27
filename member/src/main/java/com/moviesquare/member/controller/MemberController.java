package com.moviesquare.member.controller;

import java.util.Set;

import com.moviesquare.member.model.MemberDto;
import com.moviesquare.member.security.AuthenticationCustom;
import com.moviesquare.member.security.AuthenticationRequest;
import com.moviesquare.member.security.GrantedAuthorityCustom;
import com.moviesquare.member.service.JwtService;
import com.moviesquare.member.service.MemberService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("member")
@Log4j2
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtService jwtService;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    @PostMapping("/authenticate")
    public AuthenticationCustom authenticateMember(@RequestBody AuthenticationRequest aunthenticationRequest) {

        Authentication authentication= new UsernamePasswordAuthenticationToken(
                    aunthenticationRequest.getName() ,aunthenticationRequest.getPw()
        );
        try {
            Authentication authResult =daoAuthenticationProvider.authenticate(authentication);
            AuthenticationCustom authenticationCustom= new AuthenticationCustom(authResult);
            System.out.println("***");
            System.out.println(authenticationCustom);
            return authenticationCustom;
        }catch(AuthenticationException e){
            throw new RuntimeException(e);
        } 
 
    }

    @PostMapping("/jwt")
    public String getJwt(@RequestBody AuthenticationCustom authenticationCustom) {
        log.info("authentication succeeded. returns jwt");
        System.out.println(authenticationCustom);
        return jwtService.getJwt(authenticationCustom);
    }

    @PostMapping("/authenticate/jwt")
    public Set<GrantedAuthorityCustom> authenticateJwt(String jwt) {
        log.info("jwt authentication begins");
        return jwtService.authenticateJwt(jwt);
    }

    /*
     * 회원가입 메서드 이름이 중복이면 false
     */
    @PostMapping("/join")
    public boolean join(MemberDto dto) {
        return memberService.join(dto);
    }

    /* true를 return 하고 있는데 사실상 중요하지 않다 */
    @GetMapping("/header")
    @ResponseBody
    public String isHeaderPresent() {
        return "true";
    }

}
