package com.moviesquare.moviesquare.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moviesquare.moviesquare.clients.UserAPI;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserAPI userAPI;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.info("JwtUsernameAndPasswordAuthenticationFilter is actived");
        // Null 검사를 할 필요가 없다. null 이 안넘어오게 했기 때문에
        String name = request.getParameter("username");
        String pw = request.getParameter("password");

        AuthenticationRequest aunthenticationRequest = new AuthenticationRequest(name, pw);

        try {
            AuthenticationCustom authResult = userAPI.authenticate(aunthenticationRequest);

            /* 인증에 성공하면 이름을 쿠키에 저장한다 */
            Cookie cookie = new Cookie("username", name);
           
            /* cookie.setHttpOnly(true);
             * X-AUTHORIZATION-TOKEN 쿠키와 다르게 username 쿠키는 JS에서 가져올수 있게 했다 왜냐면 
             * JWT는 세션을 사용하지 않기 때문에 thymeleaf-extras-springsecurity를 사용하는데 애를 먹고있다
             * 아무리 해도 사용자의 정보를 화면에 뿌릴 방법을 못찾겠다
             * 그렇다고 세션을 허용하면 JWT를 쓰는 의미가 없다
             */ 
            cookie.setMaxAge(60 * 60 * 24 * 30);
            cookie.setPath("/");
            response.addCookie(cookie);

            Authentication authentication = new UsernamePasswordAuthenticationToken(name,null,null);

            SecurityContextHolder.getContext().setAuthentication(authentication);


            return authResult;

        }

        catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        AuthenticationCustom authenticationCustom = new AuthenticationCustom(authResult);
        String token = userAPI.getJwt(authenticationCustom);
        response.addHeader("X-AUTHORIZATION-TOKEN", "Bearer " + token);

        Cookie cookie = new Cookie("X-AUTHORIZATION-TOKEN", token);// 쿠키값은 공백을 포함못한다
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24 * 30); // 유효기간 1달
        cookie.setPath("/"); // 모든 경로에서 접근 가능 하도록 설정
        response.addCookie(cookie);
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(authResult.getPrincipal(), null,
                authResult.getAuthorities());


        SecurityContextHolder.getContext().setAuthentication(authentication);
        response.sendRedirect("/main");
        
   

    }
}