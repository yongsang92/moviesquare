package com.moviesquare.america.repository;
import java.util.List;

import com.moviesquare.america.models.Categories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoriesRepo  extends CrudRepository<Categories,Integer>{
    
    @Query("select c.movie.id from Categories c where c.tag.id=?1")
    List<Integer> findMovieIdByTag(Integer tagId);
}


