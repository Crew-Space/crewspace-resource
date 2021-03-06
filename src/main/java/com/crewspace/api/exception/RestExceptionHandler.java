package com.crewspace.api.exception;

import static com.crewspace.api.constants.ExceptionCode.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.crewspace.api.constants.ExceptionCode;
import com.crewspace.api.dto.BaseResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<BaseResponse> handleCustomException(CustomException e, HttpServletRequest request) {
        log.warn(String.format("[%s Error] : %s %s", e.getExceptionCode().getStatus(), request.getMethod(), request.getRequestURI()));
        return BaseResponse.toCustomErrorResponse(e.getExceptionCode());
    }

    // request param
    @ExceptionHandler(value = { MissingServletRequestParameterException.class })
    protected ResponseEntity<BaseResponse> handleMissingRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        log.warn(String.format("[%s Error] : %s %s", NO_REQUIRED_PARAMETER.getStatus(), request.getMethod(), request.getRequestURI()));
        return BaseResponse.toBasicErrorResponse(NO_REQUIRED_PARAMETER.getStatus(), NO_REQUIRED_PARAMETER.getMsg());
    }

    @ExceptionHandler(value = { MissingRequestHeaderException.class })
    protected ResponseEntity<BaseResponse> handleMissingRequestHeaderException(MissingRequestHeaderException e, HttpServletRequest request) {
        log.warn(String.format("[%s Error] : %s %s", NO_SPACE_ID_HEADER.getStatus(), request.getMethod(), request.getRequestURI()));
        return BaseResponse.toBasicErrorResponse(NO_SPACE_ID_HEADER.getStatus(), NO_SPACE_ID_HEADER.getMsg());
    }

    // @RequestBody valid ??????
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    protected ResponseEntity<BaseResponse> handleMethodArgNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.warn(String.format("[400 Error] : %s %s", request.getMethod(), request.getRequestURI()));
        return BaseResponse.toBasicErrorResponse(HttpStatus.BAD_REQUEST, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    // @ModelAttribute valid ??????
    @ExceptionHandler(value = { BindException.class })
    protected ResponseEntity<BaseResponse> handleMethodArgNotValidException(BindException e, HttpServletRequest request) {
        log.warn(String.format("[400 Error] : %s %s", request.getMethod(), request.getRequestURI()));
        return BaseResponse.toBasicErrorResponse(HttpStatus.BAD_REQUEST, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    // 404 Error Handler
    @ExceptionHandler(value = { NoHandlerFoundException.class } )
    protected ResponseEntity<BaseResponse> handleNotFoundException(NoHandlerFoundException e, HttpServletRequest request){
        log.warn(String.format("[404 Error] : %s %s", request.getMethod(), request.getRequestURI()));
        return BaseResponse.toBasicErrorResponse(NOT_FOUND, request.getMethod()+ " " +request.getRequestURI()+ " ????????? ?????? ??? ????????????.");
    }

    // Rest Exception Handler
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<BaseResponse> handleException(Exception e, HttpServletRequest request) {
        log.error("[500 Error] : " + request.getMethod() + " " + request.getRequestURI() + " " + e.getMessage());
        log.error(e.toString());
        return BaseResponse.toBasicErrorResponse(INTERNAL_SERVER_ERROR, request.getRequestURI()+ " ?????? ????????? ?????? ?????? ??? ????????? ??????????????????.");
    }
}
