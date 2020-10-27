package com.moviesquare.korea.controllers;
import java.io.IOException;
import java.util.List;

import com.moviesquare.korea.models.Movie;
import com.moviesquare.korea.models.Story;
import com.moviesquare.korea.services.MovieServices;
import com.moviesquare.korea.services.StoryGetServices;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StoryController {

    private final StoryGetServices storyGetServices;
    private final MovieServices MovieServices;

    @GetMapping("/getstories")
    @ResponseBody
    public List<Story> getStories(@RequestParam(defaultValue = "0") Integer page) throws IOException {
        return storyGetServices.getStories(page);
    }

    @GetMapping("/getstoriestotal")
    @ResponseBody
    public int getTotal() {
        return storyGetServices.getTotal();
    }

    // FIXME LATER: 스토리를 클릭하면 스토리의 제목, 등록시간을 관련된 움짤중 첫번째 움짤의 제목에 포함시킨다.
    // 그러면 DB와의 통신횟수를 줄일 수 있다
    @GetMapping("/story/{id}")
    @ResponseBody
    public List<Movie> getGifsRelStory(@PathVariable Integer id) throws IOException {
        Story story = storyGetServices.getStory(id);
        List<Movie> movies = story.getMovies();
        
        movies.get(0).setTitle(story.getRegdate()+","+story.getTitle()+","+movies.get(0).getTitle());
        System.out.println(movies.get(0)); 
        MovieServices.imgsTobyteArray(movies);
        return movies;
    }

    // 스토리보다 먼저 생성된 5개의 스토리를 가져와서 스토리화면 하단에 뿌려준다
    @GetMapping("/relstories/{id}")
    @ResponseBody
    public List<Story> getRelStories(@PathVariable Integer id) throws IOException {
        return storyGetServices.getOldStories(id);
    }

}