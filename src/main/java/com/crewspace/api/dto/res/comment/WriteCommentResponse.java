package com.crewspace.api.dto.res.comment;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import org.springframework.http.ResponseEntity;

public class WriteCommentResponse extends BaseResponse {

    private WriteCommentResponse(Boolean success, String msg) {
        super(success, msg);
    }

    public static WriteCommentResponse of(Boolean success, String msg){
        return new WriteCommentResponse(success, msg);
    }

    public static ResponseEntity<WriteCommentResponse> newResponse(SuccessCode code){
        return new  ResponseEntity(WriteCommentResponse.of(true, code.getMsg()), code.getStatus());
    }
}
