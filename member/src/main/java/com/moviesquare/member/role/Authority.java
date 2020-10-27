package com.moviesquare.member.role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Authority {

    GIF_READ("gif:read"),STORY_READ("story:read"),
    POST_GIF("post:gif"),POST_STORY("post:story");

    private final String authority;
    
   
}
