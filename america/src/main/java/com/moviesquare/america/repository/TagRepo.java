package com.moviesquare.america.repository;
import java.util.Optional;

import com.moviesquare.america.models.Tag;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TagRepo  extends CrudRepository<Tag,Integer>{
    
    @Query("select t.id from Tag t where t.tag=?1")
    Integer findIdByTag(String tag);

    @Query("select t from Tag t where t.tag=?1")
    Optional<Tag> findTagByTag(String tag);
}