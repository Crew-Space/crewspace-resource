package com.crewspace.api.jwt;


import com.crewspace.api.constants.ExceptionCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenValidator tokenValidator;

    // 해당 클래스는 JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
    // request header 에서 at를 꺼내고 여러가지 검사 이후 유저 정보 꺼내 컨텍스트에 저장.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String jwt = resolveToken(request);

        try{
            if (StringUtils.hasText(jwt) && tokenValidator.validateToken(jwt)) {
                Authentication authentication = tokenValidator.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            request.setAttribute("exception", ExceptionCode.WRONG_TYPE_TOKEN);
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", ExceptionCode.EXPIRED_TOKEN);
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", ExceptionCode.UNSUPPORTED_TOKEN);
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", ExceptionCode.WRONG_TOKEN);
            log.info("JWT 토큰이 잘못되었습니다.");
        } catch(Exception e) {
            log.error("===========================================");
            log.error("JwtFilter - doFilterInternal() 오류 발생");
            log.error("Exception message : {}", e.getMessage());
            log.error("===========================================");
            request.setAttribute("exception", ExceptionCode.UNKNOWN_ERROR);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

