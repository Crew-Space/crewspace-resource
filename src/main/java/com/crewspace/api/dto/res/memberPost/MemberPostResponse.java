package com.crewspace.api.dto.res.memberPost;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter

public class MemberPostResponse extends BaseResponse {

    private MemberPostResponse(Boolean success, String msg) {
        super(success, msg);
    }

    public static MemberPostResponse of(Boolean success, String msg){
        return new MemberPostResponse(success, msg);
    }

    public static ResponseEntity<MemberPostResponse> newResponse(SuccessCode code){
        return new  ResponseEntity(MemberPostResponse.of(true, code.getMsg()), code.getStatus());
    }
}
