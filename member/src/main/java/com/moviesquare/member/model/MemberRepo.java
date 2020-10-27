package com.moviesquare.member.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member,Long>{

    Optional<Member> findByName(String name);
    
}
