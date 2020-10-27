package com.moviesquare.marvel.repository;
import com.moviesquare.marvel.models.Movie;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepo extends CrudRepository<Movie,Integer> {
    List<Movie> findAll();


    List<Movie> findAll(Pageable paging);

    
    @Query("select j from Movie j where j.id in ?1")
    List<Movie> findAllWithPaging(List<Integer>Ids, Pageable paging);
    List<Movie> findTop2ByOrderByCountDesc();

}