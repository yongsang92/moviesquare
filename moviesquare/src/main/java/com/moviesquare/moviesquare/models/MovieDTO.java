package com.moviesquare.moviesquare.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data 
public class MovieDTO {

    String title;  
    String from; // 한, 일, 미
    
    List<String> tags=new ArrayList<>(); 

    
    public Movie toEntity(){
        Movie m=new Movie();
        m.setTitle(this.title);
        m.setFrom(this.from);
        String str=tags.stream().map(n->String.valueOf(n)).collect(Collectors.joining(","));
        m.setTags(str);
        return m;
    }
}