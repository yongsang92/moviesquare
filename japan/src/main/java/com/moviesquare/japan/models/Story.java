package com.moviesquare.japan.models;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Story extends Time {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    Integer id;
    String title,mainImgPath;
    
    @Transient
    byte[] imgbyteArray;

    @OneToMany(mappedBy = "story",cascade = CascadeType.PERSIST)
    @JsonIgnore
    @Exclude
    List<Movie> movies=new ArrayList<>();

    public void addMovie(Movie movie){
        movies.add(movie);
        movie.setStory(this);
    }
    
}