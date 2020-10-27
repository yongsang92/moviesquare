package com.moviesquare.japan.converter;
import java.util.List;

import lombok.Data;

@Data
public class StoryDTO {
    String title, nation;
    String[] comments, imgTitles, tags;

}