package com.moviesquare.korea.converter;
import java.util.Arrays;
import java.util.List;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

public class MovieDTOConverter {

  @Component
  public static class StringToMovieDTO implements Converter<String, MovieDTO> {

    @Override
    public MovieDTO convert(String source) {

      String a = source.substring(9);
      String b = a.substring(0, a.length() - 1);
      String title = b.substring(b.indexOf("=") + 1, b.indexOf(",")); // title
      String from = b.substring(b.indexOf(",") + 7, b.lastIndexOf("=") - 6); // from

      String tags = b.substring(b.lastIndexOf("=") + 2, b.length() - 1); // tags
      List<String> tagsList = Arrays.asList(tags.split(","));

      MovieDTO dto = new MovieDTO();
      dto.setTitle(title);
      dto.setFrom(from);
      dto.setTags(tagsList);
      return dto;
    }

  }
}