package com.crewspace.api.jwt;

import com.crewspace.api.constants.ExceptionCode;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {

        String exception = (String)request.getAttribute("exception");

        if(exception==null){
            log.info("알 수 없는 인증 에러입니다.");
            setResponse(response, ExceptionCode.UNKNOWN_ERROR);
        }else if(exception.equals(ExceptionCode.WRONG_TYPE_TOKEN.getCode())){
            setResponse(response, ExceptionCode.WRONG_TYPE_TOKEN);
        }else if(exception.equals(ExceptionCode.EXPIRED_TOKEN.getCode())){
            setResponse(response, ExceptionCode.EXPIRED_TOKEN);
        }else if(exception.equals(ExceptionCode.UNSUPPORTED_TOKEN.getCode())){
            setResponse(response, ExceptionCode.UNSUPPORTED_TOKEN);
        }else{
            setResponse(response, ExceptionCode.ACCESS_DENIED);
        }
        return;
    }

    private void setResponse(HttpServletResponse response, ExceptionCode exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.getWriter().println("{ \"msg\" : \"" + exceptionCode.getMsg()
            + "\", \"timestamp\" : \"" + LocalDateTime.now() + "\" \n }");
    }

}
