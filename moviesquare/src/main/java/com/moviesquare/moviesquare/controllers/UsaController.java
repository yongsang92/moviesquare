package com.moviesquare.moviesquare.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moviesquare.moviesquare.clients.UsaClients;
import com.moviesquare.moviesquare.models.Movie;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("usa")
@RequiredArgsConstructor
public class UsaController {
    private final UsaClients movieClients;

    /* 6개씩 끊어서 전체 조회 */
    
    @GetMapping("/movies")
    public String movies(HttpServletRequest request, HttpServletResponse response,@RequestParam(defaultValue = "0") Integer page,Model model)
            throws Exception {
        List<Movie> movies = movieClients.getMovies(page);
        int total = movieClients.getTotal();
        model.addAttribute("movies", movies);
        model.addAttribute("total", total);
        return "display/usa";
    } 



    /* 6개 추가 조회 */
    @GetMapping("/add")
    @ResponseBody
    public List<Movie> infiniteScroll(Integer page) throws IOException {
        return movieClients.getMovies(page);

    }

    /* 영화 클릭 */ 
    @GetMapping("/movie/{id}")
    public String movie(@PathVariable Integer id, Model model) {
        Movie movie = movieClients.getMovie(id);
        model.addAttribute("movie", movie); 
        return "display/gif";

    }



}