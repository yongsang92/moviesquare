package com.moviesquare.japan.models;
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

    @Column(name="count")
    Long count=0L;

    @OneToMany(mappedBy = "tag")
    @ToString.Exclude
    List<Categories> categories=new ArrayList<>();
    
}