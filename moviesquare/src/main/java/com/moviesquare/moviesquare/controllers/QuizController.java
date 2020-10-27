package com.moviesquare.moviesquare.controllers;

import java.util.List;

import com.moviesquare.moviesquare.clients.JpClients;
import com.moviesquare.moviesquare.clients.QuizAPI;
import com.moviesquare.moviesquare.models.Movie;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("quiz")
@RequiredArgsConstructor
public class QuizController {

    // private final QuizAPI api;
    private final JpClients jp;

    @GetMapping("/levelone")
    public String one(Model model){
        // List<Movie>movies=api.getlevelOne();
        List<Movie>movies=jp.getMovies(0);
        model.addAttribute("movies",movies);
        return "/display/quiz";
    }
    
    // @GetMapping("/leveltwo")
    // public String two(Model model){
    //     List<Movie>movies=api.getlevelTwo();
    //     model.addAttribute("movies",movies);
    //     return "/display/quiz";

    // }
    // @GetMapping("/levelthree")
    // public String three(Model model){
    //     List<Movie>movies=api.getlevelThree();
    //     model.addAttribute("movies",movies);
    //     return "/display/quiz";

    // }
}
