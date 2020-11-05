package com.moviesquare.korea.controllers;
import java.io.IOException;
import java.util.List;

import com.moviesquare.korea.models.Movie;
import com.moviesquare.korea.services.MovieServices;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("korea")
@RequiredArgsConstructor
public class BaseController {

    private final MovieServices MovieServices;
 

    /* 움짤 클릭 */
    @GetMapping("/movie/{id}")
    @ResponseBody
    public Movie getMovie(@PathVariable Integer id) throws IOException {
        return MovieServices.getMovie(id);
    }

    /* 전체 조회 */
    @GetMapping("/movies")
    @ResponseBody
    public List<Movie> getMoviesToScroll(@RequestParam(defaultValue = "0") Integer page) throws IOException {
        return MovieServices.getMovies(page);
    }
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
 

    /* 전체 개수 반환 */
    @GetMapping("/total")
    @ResponseBody
    public int getTotal() throws IOException {
        return MovieServices.getTotal();
    }
    
  

}