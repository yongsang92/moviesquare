package com.moviesquare.america.converter;

import java.util.List;

import lombok.Data;

@Data
public class StoryDTO {
    String title, nation;
    String[] comments, imgTitles, tags;

}