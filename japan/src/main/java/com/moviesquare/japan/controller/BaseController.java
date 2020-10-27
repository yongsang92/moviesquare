package com.moviesquare.japan.controller;

import com.moviesquare.japan.repository.MovieRepo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("jp")
@RequiredArgsConstructor
public class BaseController {

    private final MovieRepo movieRepo;


    @GetMapping("test")
    @ResponseBody
    public String asd(){
        return movieRepo.findById(1).get().getTitle(); 
    }





}