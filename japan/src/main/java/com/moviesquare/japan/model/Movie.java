package com.moviesquare.japan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


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
public class Movie {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    Integer id;
    
    String title,imgPath,tags,comments;
    @Transient
    byte[] imgbyteArray;

    @ManyToOne
    @JoinColumn(name="STORY_ID")
    @Exclude
    Story story;
    
  
    @Column(name="count")
    Long count=0L;

    @Transient
    String from;

}
