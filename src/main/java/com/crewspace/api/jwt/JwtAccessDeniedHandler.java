package com.crewspace.api.jwt;


import com.crewspace.api.constants.ExceptionCode;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        log.warn("================================================================");
        log.warn("User(" + auth.getName() + ") 가 접근 제한이 걸려있는 URL에 접근했습니다.");
        log.warn("PROTECTED : " + request.getRequestURI());
        log.warn("================================================================");
        setResponse(response, ExceptionCode.FORBIDDEN_ACCESS);
        return;
    }

    private void setResponse(HttpServletResponse response, ExceptionCode exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println("{ \"msg\" : \"" + exceptionCode.getMsg()
            + "\", \"timestamp\" : \"" + LocalDateTime.now() + "\" \n }");
    }

}
