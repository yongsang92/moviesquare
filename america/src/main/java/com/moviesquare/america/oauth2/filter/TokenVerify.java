package com.moviesquare.america.oauth2.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@WebFilter(
    urlPatterns = {"/makegif","/makestory"}
)
@Component
@Log4j2
public class TokenVerify implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest HttpServletRequest = (HttpServletRequest) request;

        String jwt = HttpServletRequest.getHeader("jwt");


        String uri = "https://oauth2.googleapis.com/tokeninfo?id_token="+jwt;
        RestTemplate restTemplate = new RestTemplate();
        // ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        chain.doFilter(request, response);
    }

}
