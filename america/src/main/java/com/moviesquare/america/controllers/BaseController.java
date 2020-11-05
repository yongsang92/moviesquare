package com.moviesquare.america.controllers;
import java.util.List;

import com.moviesquare.america.models.Movie;
import com.moviesquare.america.services.MovieServices;

import java.io.IOException;




import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
 

@Controller
@RequestMapping("usa")
@RequiredArgsConstructor
public class BaseController {

    private final MovieServices MovieServices;
    
    
   
    @GetMapping("/movie/{id}")
    @ResponseBody
    public Movie getMovie(@PathVariable Integer id) throws IOException {
        return MovieServices.getMovie(id);
    }

    /* 조회수 TOP2 움짤 반환 */
    @GetMapping("/trending")
    @ResponseBody
    public List<Movie> getTrendingMovie() throws IOException {
        return MovieServices.getTrending();
    }

    /* 새로운거 2개 움짤 반환 */
    @GetMapping("/brandnew")
    @ResponseBody
    public List<Movie> getBrandnew() throws IOException {
        return MovieServices.getBrandnew();
    }

    @GetMapping("/movies")
    @ResponseBody
    public List<Movie> getMovies(@RequestParam(defaultValue = "0") Integer page) throws Exception {
        
        return MovieServices.getMovies(page);
    }

    @GetMapping("/total")
    @ResponseBody
    public int getTotal() throws IOException {
        return MovieServices.getTotal();
    }

}