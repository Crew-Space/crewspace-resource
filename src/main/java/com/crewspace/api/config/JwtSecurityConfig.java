package com.crewspace.api.config;

import com.crewspace.api.jwt.JwtFilter;
import com.crewspace.api.jwt.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenValidator tokenValidator;

    @Override
    public void configure(HttpSecurity http) {
        JwtFilter customFilter = new JwtFilter(tokenValidator);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
