package com.moviesquare.moviesquare.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moviesquare.moviesquare.models.Movie;
import com.moviesquare.moviesquare.models.Story;
import com.moviesquare.moviesquare.services.MainControllerServices;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("main")
@RequiredArgsConstructor
public class MainController {

    private final MainControllerServices services;

    // TODO: 메인화면에 스토리 말고 인기있는 검색바 하단에 움짤을 보여줘야 한다 . 슬라이더는 만들어 놓았으니 움짤만 전달하면 된다
    // 서비스당 3개씩 총 12개를 가져오면 된다 일단은
    @GetMapping("")
    public String main(HttpServletRequest request, HttpServletResponse response, Model model,
            @RequestParam(defaultValue = "0") Integer page) throws Exception {
        model.addAttribute("stories", services.getStories(page));
        model.addAttribute("total", services.getStoriesTotal());
        return "main/homepage";
    }

 
    @GetMapping("/storyadd")
    @ResponseBody
    public List<Story> infiniteScroll(Integer page) throws InterruptedException {
        return services.getStories(page);
    }

    @GetMapping("/trending")
    @ResponseBody
    public List<Movie> getTrending() {
        return services.getTrendingGifs();

    }

    @GetMapping("/brandnew")
    @ResponseBody
    public List<Movie> getBrandnew() {
        return services.getBrandnewGifs();

    }

    // TODO: 스토리 클릭 로직
    // 스토리 화면 하단에 스토리 보다 먼저 생성된 스토리 5개를 가져와야 된다
    @GetMapping("/story/{from}/{id}")
    public String getStory(@PathVariable(name = "from") String from, @PathVariable(name = "id") Integer id,
            Model model) {
        model.addAttribute("story", services.getStory(id, from));

        // 현재 스토리보다 먼저 생성된 스토리 5개를 하단에 뿌린다
        model.addAttribute("relstories", services.getRelStories(id, from));
        model.addAttribute("from", from);
        
        return "main/story";
    }

    @GetMapping("/accessdenied")
    public String accessdenied(){
        return "login/accessdenied";
    }
}
