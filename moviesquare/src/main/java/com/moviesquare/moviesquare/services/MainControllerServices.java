package com.moviesquare.moviesquare.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.moviesquare.moviesquare.clients.JpClients;
import com.moviesquare.moviesquare.clients.KrClients;
import com.moviesquare.moviesquare.clients.MarvelClients;
import com.moviesquare.moviesquare.clients.UsaClients;
import com.moviesquare.moviesquare.models.Movie;
import com.moviesquare.moviesquare.models.Story;

@Service
@RequiredArgsConstructor
public class MainControllerServices {

    private final MarvelClients marvel;
    private final KrClients kr;
    private final JpClients jp;
    private final UsaClients usa;

    public List<Story> getStories(int page) {

        List<Story> result = new ArrayList<>();

        List<Story> Temporary = marvel.getStories(page);

        Iterator<Story> iter = Temporary.iterator();
        while (iter.hasNext()) {
            iter.next().setFrom("marvel");
        }
        result.addAll(Temporary);

        Temporary = usa.getStories(page);
        iter = Temporary.iterator();
        while (iter.hasNext()) {
            iter.next().setFrom("usa");
        }
        result.addAll(Temporary);

        Temporary = jp.getStories(page);
        iter = Temporary.iterator();
        while (iter.hasNext()) {
            iter.next().setFrom("japan");
        }
        result.addAll(Temporary);

        Temporary = kr.getStories(page);
        iter = Temporary.iterator();
        while (iter.hasNext()) {
            iter.next().setFrom("korea");
        }
        result.addAll(Temporary);

        return result;

    }

    public List<Movie> getStory(Integer id, String from) {
        switch (from) {
            case "korea":
                return kr.getStory(id);
            case "japan":
                return jp.getStory(id);
            case "usa":
                return usa.getStory(id);
            case "marvel":
                return marvel.getStory(id);
            default:
                return List.of();
        }
    }

    public int getStoriesTotal() {
        return marvel.getStoriesTotal() + kr.getStoriesTotal() + jp.getStoriesTotal() + usa.getStoriesTotal();
    }

    public List<Movie> getTrendingGifs() {
        List<Movie> result = new ArrayList<>();
        result.addAll(marvel.getTrendingMovie());
        result.addAll(kr.getTrendingMovie());
        result.addAll(jp.getTrendingMovie());
        result.addAll(usa.getTrendingMovie());
        return result;
    }

    public List<Movie> getBrandnewGifs() {
        List<Movie> result = new ArrayList<>();
        result.addAll(marvel.getBrandnewGifs());
        result.addAll(kr.getBrandnewGifs());
        result.addAll(jp.getBrandnewGifs());
        result.addAll(usa.getBrandnewGifs());
        return result;
    }


    public List<Story> getRelStories(Integer id,String from){
        switch (from) {
            case "korea":
                return kr.getRelStories(id);
            case "japan":
                return jp.getRelStories(id);
            case "usa":
                return usa.getRelStories(id);
            case "marvel":
                return marvel.getRelStories(id);
            default:
                return List.of();
        }


    }

}