package com.moviesquare.america.controllers;
import java.io.IOException;
import java.util.List;

import com.moviesquare.america.models.Movie;
import com.moviesquare.america.services.MovieServices;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("usatags")
@RequiredArgsConstructor
public class TagController {

    private final MovieServices movieServices;

    /* 카테고리별 영화 조회 */
    @GetMapping("/movies")
    @ResponseBody
    public List<Movie> getMovieByCategories(@RequestParam String tag, @RequestParam(defaultValue = "0") int page)
            throws IOException {
        return movieServices.getMovieByCategories(tag, page);

    }

    @GetMapping("/total")
    @ResponseBody
    public int getTagTotal(String tag) throws IOException {
        return movieServices.getTagTotal(tag);
    }

 
}