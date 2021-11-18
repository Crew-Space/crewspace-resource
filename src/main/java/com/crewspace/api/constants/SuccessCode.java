package com.crewspace.api.constants;

import static org.springframework.http.HttpStatus.OK;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    TEST_SUCCESS(OK, "성공~");

    private final HttpStatus status;
    private final String msg;
}
