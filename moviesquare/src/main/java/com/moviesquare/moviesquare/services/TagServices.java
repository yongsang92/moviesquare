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

@Service
@RequiredArgsConstructor
public class TagServices {

    private final MarvelClients marvel;
    private final KrClients kr;
    private final JpClients jp;
    private final UsaClients usa;

    public List<Movie> getMovies(String tag,int page) {

        List<Movie> result = new ArrayList<>();

        List<Movie> Temporary = marvel.getTagMovies(tag, page);

        Iterator<Movie> iter = Temporary.iterator();
        while (iter.hasNext()) {
            iter.next().setFrom("marvel");
        }
        result.addAll(Temporary);

        
        Temporary = usa.getTagMovies(tag, page);
        iter = Temporary.iterator();
        while (iter.hasNext()) {
            iter.next().setFrom("usa");
        }
        result.addAll(Temporary);

        Temporary = jp.getTagMovies(tag, page);
        iter = Temporary.iterator();
        while (iter.hasNext()) {
            iter.next().setFrom("japan");
        }
        result.addAll(Temporary);

        Temporary = kr.getTagMovies(tag, page);
        iter = Temporary.iterator();
        while (iter.hasNext()) {
            iter.next().setFrom("korea");
        }
        result.addAll(Temporary);

        return result;
      
    }
    public int getTagTotal(String tag){
        return marvel.getMovieTagTotal(tag) + kr.getMovieTagTotal(tag) + jp.getMovieTagTotal(tag)
        + usa.getMovieTagTotal(tag);
    }

}