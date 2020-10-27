package com.moviesquare.moviesquare.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Movie {
    Integer id;
    String title,imgPath,tags,from,comments;
    @ToString.Exclude
    byte[] imgbyteArray;
}