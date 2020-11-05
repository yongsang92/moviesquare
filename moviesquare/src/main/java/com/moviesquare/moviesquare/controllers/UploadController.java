package com.moviesquare.moviesquare.controllers;

import javax.transaction.Transactional;

import com.moviesquare.moviesquare.models.MovieDTO;
import com.moviesquare.moviesquare.models.StoryDTO;
import com.moviesquare.moviesquare.services.UploadServices;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class UploadController {

    private final UploadServices uploadServices;

    @GetMapping("makegif")
    public String makegif() {
        return "upload/gif";
    }
   
    /* 
    1. uploadgif로 post요청을 보낼 때 JWT를 헤더에 포함해서 요청해야 한다. 
    2. JWT의 값은 X-AUTHORIZATION-TOKEN쿠키에 있따
    3. 그러니 uploadgif로 post요청을 보내면 쿠키에서 값을 꺼내서 검증을 진행한다
    */
    @PostMapping(value = "/uploadgif", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public String makegif(@ModelAttribute("dto") MovieDTO dto, @RequestPart("file") MultipartFile file) {
        
        System.out.println("asdasdsadasdasd");
      
        uploadServices.makeGif(dto, file);

        return "redirect:/makegif";
    }

    @GetMapping("makestory")
    public String makestory() {
        return "upload/story";
    }

    @PostMapping(value = "/uploadStory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public String makestory(String nation, String title, String[] imgTitle, String[] gifComments, String[] tags,
            @RequestPart(name = "file") MultipartFile[] file) {
                log.info(gifComments);
        StoryDTO dto = uploadServices.makeStoryDTO(gifComments, title, nation, imgTitle, tags);
        uploadServices.makeStroy(dto, file);

    

        return "redirect:/makestory";
    }

}