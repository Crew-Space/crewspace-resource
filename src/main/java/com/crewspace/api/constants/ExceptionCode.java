package com.crewspace.api.constants;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    /* 401 - 인증 실패 */
    // token 관련
    WRONG_TYPE_TOKEN(UNAUTHORIZED, "잘못된 JWT 서명을 가진 토큰입니다."),
    EXPIRED_TOKEN(UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_TOKEN(UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다."),
    WRONG_TOKEN(UNAUTHORIZED, "JWT 토큰이 잘못되었습니다."),
    UNKNOWN_ERROR(UNAUTHORIZED, "알 수 없는 요청 인증 에러! 헤더에 토큰을 넣어 보냈는지 다시 한 번 확인해보세요."),
    ACCESS_DENIED(UNAUTHORIZED, "접근이 거절되었습니다."),

    /* 403 - 허용되지 않은 접근 */
    FORBIDDEN_ACCESS(FORBIDDEN, "허용되지 않은 접근입니다."),

    /* 404 - 찾을 수 없는 리소스 */
    MEMBER_EMAIL_NOT_FOUND(NOT_FOUND, "가입되지 않은 이메일입니다."),
    UNVALID_SPACE_CODE(NOT_FOUND, "유효하지 않은 초대 코드입니다.");

    private final HttpStatus status;
    private final String msg;
}
