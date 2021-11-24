package com.crewspace.api.constants;

import static org.springframework.http.HttpStatus.OK;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    CREATE_SPACE_SUCCESS(OK, "동아리 생성을 성공했습니다."),
    VALID_SPACE_CODE(OK, "올바른 초대 코드입니다."),
    LOAD_REGISTER_INFO_SUCCESS(OK, "동아리 회원 가입 정보를 로드했습니다."),
    ENTER_SPACE_SUCEESS(OK, "동아리 가입을 성공했습니다."),
    LOAD_SPACES_SUCCESS(OK, "가입된 동아리 리스트를 불러왔습니다."),
    CREATE_POST_CATEGORY_SUCCESS(OK, "게시글 카테고리 생성에 성공했습니다"),
    LOAD_POST_CATEGORIES_SUCCES(OK, "게시글 카테고리 조회에 성공했습니다."),
    WRITE_COMMUNITY_POST_SUCCESS(OK, "커뮤니티 게시글 작성을 성공했습니다."),
    WRITE_NOTICE_POST_SUCCESS(OK, "공지 게시글 작성을 성공했습니다."),
    SAVE_POST_SUCCESS(OK, "게시글 저장을 성공했습니다."),
    UNSAVE_POST_SUCCESS(OK, "게시글 저장 취소에 성공했습니다."),
    FIX_POST_SUCCESS(OK, "게시글 고정을 성공했습니다."),
    UNFIX_POST_SUCCESS(OK, "게시글 고정 취소를 성공했습니다."),
    READ_COMMUNITY_POST_SUCCESS(OK, "커뮤니티 게시글 상세 조회를 성공했습니다."),
    READ_NOTICE_POST_SUCCESS(OK, "공지 게시글 상세 조회를 성공했습니다."),
    ENTER_SPACE_MAIN(OK, "동아리 입장을 성공했습니다!!!");

    private final HttpStatus status;
    private final String msg;
}
