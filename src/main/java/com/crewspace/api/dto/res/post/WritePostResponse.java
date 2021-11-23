package com.crewspace.api.dto.res.post;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class WritePostResponse extends BaseResponse {

    private WritePostResponse(Boolean success, String msg) {
        super(success, msg);
    }

    public static WritePostResponse of(Boolean success, String msg){
        return new WritePostResponse(success, msg);
    }

    public static ResponseEntity<WritePostResponse> newResponse(SuccessCode code){
        return new  ResponseEntity(WritePostResponse.of(true, code.getMsg()), code.getStatus());
    }
}
