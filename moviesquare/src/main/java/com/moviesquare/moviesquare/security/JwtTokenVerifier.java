package com.moviesquare.moviesquare.security;
import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.moviesquare.moviesquare.clients.UserAPI;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class JwtTokenVerifier extends OncePerRequestFilter {

    private final UserAPI userAPI;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.info("JwtTokenVerifier is actived");

                

        if (request.getRequestURI().startsWith("/makestory") || request.getRequestURI().startsWith("/makegif")) {
            log.info("token verification is activatirng");
            Cookie[] cookies = request.getCookies();
            String token = "";

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("X-AUTHORIZATION-TOKEN"))
                        token = cookie.getValue();
                }
            }

            if (Strings.isNullOrEmpty(token)) {
                response.sendRedirect("/loginhandler");
                return;
            }

            String username = "";
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username"))
                    username = cookie.getValue();
            }

            try {
                Set<GrantedAuthorityCustom> authorities = userAPI.parseToken(token);
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            // 토큰 검증 실패 로직
            catch (JwtException e) {
                throw new IllegalStateException("Token %s cannot be trusted");
            }
            filterChain.doFilter(request, response); // 다음 단계로 전달
        } else {
            filterChain.doFilter(request, response); // 다음 단계로 전달

        }
    }

}
