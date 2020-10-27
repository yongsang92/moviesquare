package com.moviesquare.moviesquare.clients;
import java.util.List;

import com.moviesquare.moviesquare.models.Movie;


public interface QuizAPI {

	List<Movie> getlevelOne();

	List<Movie> getlevelTwo();

	List<Movie> getlevelThree();
    
}
