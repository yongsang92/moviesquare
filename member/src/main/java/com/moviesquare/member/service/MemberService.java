package com.moviesquare.member.service;
import java.util.Optional;


import com.moviesquare.member.model.Member;
import com.moviesquare.member.model.MemberDto;
import com.moviesquare.member.model.MemberRepo;
import com.moviesquare.member.role.Role;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    
    private final MemberRepo memberRepo;
    private final PasswordEncoder passwordEncoder;


    /* 
     * 회원가입 메서드 
     * 이름이 중복이면 false
     */
	public boolean join(MemberDto dto) {

        boolean exist=isPresent(dto.getName());

        if (!exist) {
            Member member= Member.builder()
                .name(dto.getName())
                .pw(passwordEncoder.encode(dto.getPw()))
                .role(Role.NEWBIE)
                .build();
            memberRepo.save(member);
            return true;
        }
        return false;
    }

    public boolean isPresent(String name) {
        Optional<Member> optional = memberRepo.findByName(name);
        return optional.isPresent();
    }


    



  

   


}
