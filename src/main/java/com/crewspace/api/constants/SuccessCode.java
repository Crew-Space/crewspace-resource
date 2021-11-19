package com.crewspace.api.constants;

import static org.springframework.http.HttpStatus.OK;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    CREATE_SPACE_SUCCESS(OK, "동아리 생성을 성공했습니다."),
    VALID_SPACE_CODE(OK, "올바른 초대 코드입니다.");

    private final HttpStatus status;
    private final String msg;
}
