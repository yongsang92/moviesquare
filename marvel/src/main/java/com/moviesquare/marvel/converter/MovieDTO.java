package com.moviesquare.marvel.converter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.moviesquare.marvel.models.Movie;

import lombok.Data;

@Data 
public class MovieDTO {

    String title;  
    String from; // 한, 일, 미  // 이게 의미가 없다  그냥 분산시킬려고 만든거다 

    List<String> tags=new ArrayList<>(); 

    
    public Movie toEntity(){
        Movie m=new Movie();
        m.setTitle(this.title);
        String str=tags.stream().map(n->String.valueOf(n)).collect(Collectors.joining(","));
        m.setTags(str);
        return m;
    }
}