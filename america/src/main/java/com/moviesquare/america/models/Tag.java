package com.moviesquare.america.models;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;

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

    @Column(name="COUNT")
    Long count=0L;
 
    @OneToMany(mappedBy = "tag")
    List<Categories> categories=new ArrayList<>();
    
}