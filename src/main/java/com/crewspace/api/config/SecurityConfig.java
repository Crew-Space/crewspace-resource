package com.crewspace.api.config;

import com.crewspace.api.jwt.JwtAccessDeniedHandler;
import com.crewspace.api.jwt.JwtAuthenticationEntryPoint;
import com.crewspace.api.jwt.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenValidator tokenValidator;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // h2 database 테스트 관련
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers("/h2-console/**", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().disable()

            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

            .authorizeRequests()
            .antMatchers("/v1/admin/").access("hasRole('ROLE_ADMIN')")// admin URL은 ADMIN 유저만 가능
            .antMatchers("/v1/**").authenticated()  // Resource 서버로 들어오는 API 에는 인증이 필요하다

            .and()
            .apply(new JwtSecurityConfig(tokenValidator))
            .and()
            .formLogin().disable()
        ;
    }
}

