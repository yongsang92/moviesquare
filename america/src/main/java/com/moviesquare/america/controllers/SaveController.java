package com.usa.movie.controllers;

import javax.transaction.Transactional;

import com.usa.movie.converter.MovieDTO;
import com.usa.movie.converter.StoryDTO;
import com.usa.movie.services.SaveServices;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SaveController {

    private final SaveServices saveServices;

    @PostMapping(value = "/makegif")
    @Transactional
    @ResponseBody
    public void saveGif(@RequestParam(name = "dto") MovieDTO dto, @RequestParam(name = "file") MultipartFile file) {
        saveServices.saveGif(dto,file);
    }
    @PostMapping(value = "/makestory")
    @Transactional
    @ResponseBody
    public void saveStory(@RequestParam(name="dto") StoryDTO dto, @RequestParam(name = "file") MultipartFile[] files){
        saveServices.saveStory(dto,files);
    }
}