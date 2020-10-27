package com.moviesquare.marvel.repository;
import java.util.List;

import com.moviesquare.marvel.models.Story;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface StoryRepo extends CrudRepository<Story, Integer> {
 
    List<Story> findAll();

    Page<Story> findAll(Pageable paging);

    List<Story> findByIdLessThan(Integer id,Pageable pageable);

}