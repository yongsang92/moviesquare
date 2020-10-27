package com.moviesquare.korea.converter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

public class StoryDTOConverter {

  @Component
  public static class StringToStoryDTO implements Converter<String, StoryDTO> {

    @Override
    public StoryDTO convert(String source) {

      String a = source.substring(9);
      String b = a.substring(0, a.length() - 1);
      String title = b.substring(b.indexOf("=") + 1, b.indexOf(",")); // title
      String tags = b.substring(b.lastIndexOf("=") + 2, b.length() - 1); // tag

      String[] tagArrWithBlank = tags.split("@");

      String[] tagArrNoneBlank = new String[tagArrWithBlank.length]; // 공백제거 해야된다
      for (int i = 0; i < tagArrWithBlank.length; i++) {
        String tagStr = tagArrWithBlank[i]; // ㅁ, 고마츠 나나,분홍 머리 # ㅁ: 은 공백 # 공백이 들어가있어서 너무 화나네
        String[] splitTag = tagStr.split(",");
        List<String> usefultags = new ArrayList<>(); // 공백이 아닌 태그만 저장하는 필드 
        for (String str : splitTag) {
          if (!str.equals("")) {
            usefultags.add(str.trim());
          }
        }
        String withBracket=usefultags.toString(); // [tag,tag1,tag2,tag3] 이런 형식이기 때문에 양쪽 괄호를 제거해주자
        String NoBracket=withBracket.substring(withBracket.indexOf("[")+1,withBracket.indexOf("]"));

        tagArrNoneBlank[i] = NoBracket;

      }
    
      String c = b.substring(b.indexOf(",") + 1, b.lastIndexOf("=") - 6); // c

      String nation = c.substring(c.indexOf("=") + 1, c.indexOf(",")); // nation

      String imgTitles = c.substring(c.lastIndexOf("=") + 2, c.length() - 1);
      String[] imgTitlesArr = imgTitles.split(",");

      String gifComments = c.substring(c.indexOf(",") + 12, c.lastIndexOf("=") - 11); 
      // 코멘트가 공백도 가능하다. 그리고 공백이라도 그냥 저장해 주기로 했다
      String[] gifCommentsArr = gifComments.split(",");

    
      StoryDTO dto = new StoryDTO();
      dto.setTitle(title);
      dto.setNation(nation);
      dto.setTags(tagArrNoneBlank);
      dto.setImgTitles(imgTitlesArr);
      dto.setComments(gifCommentsArr);

      return dto;
    }

  }
}