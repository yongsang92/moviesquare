package com.moviesquare.member.security;


import com.moviesquare.member.model.Member;
import com.moviesquare.member.model.MemberRepo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MemberUserDetailService  implements UserDetailsService{

    private final MemberRepo memberRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member=memberRepo.findByName(username).orElseThrow();
        return new ApplicationMember(member);
    }
    
}
