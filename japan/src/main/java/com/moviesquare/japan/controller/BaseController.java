package com.moviesquare.japan.controller;
import java.io.IOException;
import java.util.List;

import com.moviesquare.japan.models.Movie;
import com.moviesquare.japan.services.MovieServices;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("jp")
@RequiredArgsConstructor
@Log4j2
public class BaseController {

    private final MovieServices MovieServices;

    /* 움짤 클릭 */
    @GetMapping("/movie/{id}")
    @ResponseBody
    public Movie getMovie(@PathVariable Integer id) throws IOException {
      log.debug(" service call id: {}",id);
        return MovieServices.getMovie(id);

    }

    /* 조회수 TOP2 움짤 반환 */
    @GetMapping("/trending")
    @ResponseBody
    public List<Movie> getTrendingMovie() throws IOException {
        return MovieServices.getTrending();

    }


    /* 전체 조회 */
    @GetMapping("/movies")
    @ResponseBody
    public List<Movie> getMovies(@RequestParam(defaultValue ="0") Integer page) throws IOException {
        
        return MovieServices.getMovies(page);

    }

    /* 전체 개수 반환 */
    @GetMapping("/total")
    @ResponseBody
    public int getTotal() throws IOException {
        return MovieServices.getTotal();
    }



}