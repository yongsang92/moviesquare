package com.moviesquare.marvel.models;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Tag {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    Integer id;
    
    @Column(unique = true,nullable = false)
    String tag;

    @OneToMany(mappedBy = "tag")
    @ToString.Exclude
    List<Categories> categories=new ArrayList<>();

    @Column(name="COUNT")
    Long count=0L;
    
}