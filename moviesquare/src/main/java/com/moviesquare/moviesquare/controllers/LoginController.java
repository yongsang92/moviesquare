package com.moviesquare.moviesquare.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moviesquare.moviesquare.clients.UserAPI;
import com.moviesquare.moviesquare.security.AuthenticationRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class LoginController {

    private final UserAPI userAPI;

    @GetMapping("/loginhandler")
    public String login() {

        return "login/login";
    }

    @GetMapping("/loginerror")
    public String loginerror() {
        return "login/loginerror";
    }

  
    @GetMapping("/join")
    public String signup() {
        return "login/join";
    }

    @PostMapping("/joinhandler")
    public String signupHandle(AuthenticationRequest authenticationRequest) {

        log.info(String.format("%s is name, %s is pw", authenticationRequest.getName(), authenticationRequest.getPw()));

        boolean result = userAPI.join(authenticationRequest.getName(), authenticationRequest.getPw());
        if (!result) {
            return "redirect:/loginerror";

        }
        return "redirect:/login";
    }

    /*
     * 로그아웃 메서드 2. 세션 제거 3. 쿠키 제거
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        // 쿠키 제거
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                cookies[i].setMaxAge(0); // 유효시간을 0으로 설정
                cookies[i].setPath("/"); // 유효시간을 0으로 설정
                response.addCookie(cookies[i]); // 응답 헤더에 추가
            }
        }

        return "redirect:/main";

    }
}
