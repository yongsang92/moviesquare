package com.moviesquare.moviesquare.controllers;


import java.io.IOException;
import java.util.List;

import com.moviesquare.moviesquare.clients.JpClients;
import com.moviesquare.moviesquare.models.Movie;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("japan")
@RequiredArgsConstructor
public class JapanController {

    private final JpClients movieClients;



    @HystrixCommand(threadPoolKey = "japanPool"
    ,fallbackMethod = "fallback"
    ,threadPoolProperties={
        @HystrixProperty(name="coreSize",value="2")
        ,@HystrixProperty(name="maxQueueSize",value="3")
        }
    ,commandProperties = {
        @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="2000")
        ,@HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10")
        ,@HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="75")
        ,@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="7000")
        ,@HystrixProperty(name="metrics.rollingStats.timeInMilliseconds",value="15000")
        ,@HystrixProperty(name="metrics.rollingStats.numBuckets",value="5") 
    }
    )
    @GetMapping("/movies")
    public String movies(Model model, @RequestParam(defaultValue = "0") Integer page) {
        List<Movie> movies = movieClients.getMovies(page);
        int total = movieClients.getTotal();
        model.addAttribute("movies", movies);
        model.addAttribute("total", total);

        return "display/japan";
    }
    public String fallback(Model model, @RequestParam(defaultValue = "0") Integer page,Throwable t){
        System.out.println("error message is ");
        t.printStackTrace();
        System.out.println("fallback method is on");

        return "japan/movies";
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
 