package com.moviesquare.moviesquare.controllers;

import java.util.List;

import com.moviesquare.moviesquare.models.Movie;
import com.moviesquare.moviesquare.services.TagServices;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TagController {


    private final TagServices tagServices;

    @GetMapping("/by")
    public String tag(String tag, @RequestParam(defaultValue = "0") int page, Model model)
            throws InterruptedException {

        model.addAttribute("tag", tag);
        model.addAttribute("movies", tagServices.getMovies(tag, page));
        model.addAttribute("total", tagServices.getTagTotal(tag));
        return "tag/search";
    }

    @GetMapping("/tagadd")
    @ResponseBody
    public List<Movie> add(@RequestParam String tag, @RequestParam(defaultValue = "0") int page, Model model)
            throws InterruptedException {

                
        return tagServices.getMovies(tag, page);
    }

    @GetMapping("/tagtotal")
    @ResponseBody
    public int total(@RequestParam String tag)
            throws InterruptedException {

        return tagServices.getTagTotal(tag);
    }

}