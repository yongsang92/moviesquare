package com.moviesquare.marvel.converter;


import lombok.Data;

@Data
public class StoryDTO {
    String title, nation;
    String[] comments, imgTitles, tags;

}