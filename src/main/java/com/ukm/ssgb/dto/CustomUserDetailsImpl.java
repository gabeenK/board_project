package com.ukm.ssgb.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetailsImpl extends User {

    private final TokenDto tokenDto;

    public CustomUserDetailsImpl(TokenDto tokenDto, Collection<? extends GrantedAuthority> authorities) {
        super(String.valueOf(tokenDto.getEmail()), "", authorities);
        this.tokenDto = tokenDto;
    }
}