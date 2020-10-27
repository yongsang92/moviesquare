package com.member.www.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.member.www.role.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name,pw;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String name,String pw,Role role){
        this.name=name;
        this.pw=pw;
        this.role=role;
    }

  
}
