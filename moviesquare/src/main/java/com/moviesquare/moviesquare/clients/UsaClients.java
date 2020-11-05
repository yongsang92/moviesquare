package com.moviesquare.moviesquare.clients;
import java.util.List;

import com.moviesquare.moviesquare.models.Movie;
import com.moviesquare.moviesquare.models.MovieDTO;
import com.moviesquare.moviesquare.models.Story;
import com.moviesquare.moviesquare.models.StoryDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "americaservice")
public interface UsaClients {

    /* 가장 기본 3개 메서드 */
    @GetMapping(value = "usa/movie/{id}", consumes = "application/json")
    Movie getMovie(@PathVariable(name = "id") Integer id);

    @GetMapping(value = "usa/movies", consumes = "application/json")
    List<Movie> getMovies(@RequestParam(name = "page") Integer page);

    @GetMapping(value = "usa/total", consumes = "application/json")
    int getTotal();

    /* 태그 관련 움짤 조회 */
    @GetMapping(value = "usatags/movies", consumes = "application/json")
    List<Movie> getTagMovies(@RequestParam(name = "tag") String tag, @RequestParam(name = "page") int page);

    /* 해당 태그와 관련된 개수 반환 */
    @GetMapping(value = "usatags/total", consumes = "application/json")
    int getMovieTagTotal(@RequestParam(name = "tag") String tag);

    @GetMapping(value = "usa/trending", consumes = "application/json")
    List<Movie> getTrendingMovie();

    @GetMapping(value = "usa/brandnew", consumes = "application/json")
    List<Movie> getBrandnewGifs();

    @PostMapping(value = "makegif", consumes = "multipart/form-data")
    void makeGif(@RequestHeader("X-AUTHORIZATION-TOKEN")String jwt, @RequestParam(name = "dto") MovieDTO dto, @RequestPart(name = "file") MultipartFile file);

    @PostMapping(value = "makestory", consumes = "multipart/form-data")
    void makeStory(@RequestHeader("X-AUTHORIZATION-TOKEN")String jwt,@RequestParam(name = "dto") StoryDTO dto, @RequestPart(name = "file") MultipartFile[] file);

    @GetMapping(value = "getstories", consumes = "application/json")
    List<Story> getStories(@RequestParam(name = "page") int page);

    /*
     * TODO INFO 
     * 스토리를 가져오는데 왜 Movie 타입으로 받는지 의아할 수 있다.
     * 그 이유는 Movie 리스트의 첫번째 움짤의 제목에 스토리의 제목, 등록시간이 들어있기 때문이다
     */
    @GetMapping(value = "story/{id}", consumes = "application/json")
    List<Movie> getStory(@PathVariable(name = "id") int id);

    @GetMapping(value = "getstoriestotal", consumes = "application/json")
    int getStoriesTotal();

    @GetMapping("/relstories/{id}")
    List<Story> getRelStories(@PathVariable(name = "id") Integer id);

}
