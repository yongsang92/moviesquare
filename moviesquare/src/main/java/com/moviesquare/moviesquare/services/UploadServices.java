package com.moviesquare.moviesquare.services;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.moviesquare.moviesquare.clients.JpClients;
import com.moviesquare.moviesquare.clients.KrClients;
import com.moviesquare.moviesquare.clients.MarvelClients;
import com.moviesquare.moviesquare.clients.UsaClients;
import com.moviesquare.moviesquare.models.MovieDTO;
import com.moviesquare.moviesquare.models.StoryDTO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class UploadServices {

    private final MarvelClients marvel;
    private final KrClients kr;
    private final JpClients jp;
    private final UsaClients usa;
    private final HttpServletRequest request;

    private final static String jwtCookieName = "X-AUTHORIZATION-TOKEN";
    private final static String AuthorizationHedaer = "X-AUTHORIZATION-TOKEN";
    private final static String AuthorizationHeaderPrefix = "Bearer ";

    /* POST 요청을 하면 쿠키의 만료일을 연장시켜준다 */
    public String getTokenFromCookie() {
        String token = "";
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(jwtCookieName)) {
                token = cookie.getValue();
                cookie.setMaxAge(60 * 60 * 24 * 30);
            }
        }
        return token;
    }
    public String jwtWithBearerPrefix(){
        return AuthorizationHeaderPrefix+getTokenFromCookie();
    }


    public StoryDTO makeStoryDTO(String[] gifComments, String title, String nation, String[] imgTitle, String[] tags) {
        StoryDTO storyDTO = new StoryDTO();
        storyDTO.setComments(gifComments);
        storyDTO.setTitle(title);
        storyDTO.setNation(nation);
        storyDTO.setImgTitle(imgTitle);

        for (int i = 0; i < tags.length; i++) {
            tags[i] = tags[i] + "@"; // @ 구분자 추가
        }
        storyDTO.setTags(tags);
        return storyDTO;
    }

 
    public void makeGif(MovieDTO dto, MultipartFile file) {

        switch (dto.getFrom()) {
            case "KOR":
                kr.makeGif(jwtWithBearerPrefix(),dto, file);
                break;
            case "JAPAN":
                jp.makeGif(jwtWithBearerPrefix(),dto, file);
                break;
            case "USA":
                usa.makeGif(jwtWithBearerPrefix(),dto, file);
                break;
            case "MARVEL":
                marvel.makeGif(jwtWithBearerPrefix(),dto, file);
                break;
            default:
                break;
        }
    }

    public void makeStroy(StoryDTO dto, MultipartFile[] file) {
        switch (dto.getNation()) {
            case "KOR":
                kr.makeStory(jwtWithBearerPrefix(),dto, file);
                break;
            case "JAPAN":
                jp.makeStory(jwtWithBearerPrefix(),dto, file);
                break;
            case "USA":
                usa.makeStory(jwtWithBearerPrefix(),dto, file);
                break;
            default:
                marvel.makeStory(jwtWithBearerPrefix(),dto, file);

        }
    }

}
