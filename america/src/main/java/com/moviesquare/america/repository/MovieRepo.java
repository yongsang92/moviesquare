package com.moviesquare.america.repository;
import java.util.List;

import com.moviesquare.america.models.Movie;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepo extends CrudRepository<Movie,Integer> {
    List<Movie> findAll();


    List<Movie> findAll(Pageable paging);

    @Query("select u from Movie u where u.id in ?1")
    List<Movie> findAllWithPaging(List<Integer>Ids, Pageable paging);

    List<Movie> findTop2ByOrderByCountDesc();
}