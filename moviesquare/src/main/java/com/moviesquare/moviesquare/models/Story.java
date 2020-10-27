package com.moviesquare.moviesquare.models;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Story {

    Integer id;
    String title,imgPath,from;
    byte[] imgbyteArray;
    LocalDateTime updatedate;
}
