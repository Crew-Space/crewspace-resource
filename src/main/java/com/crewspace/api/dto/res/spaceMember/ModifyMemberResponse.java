package com.crewspace.api.dto.res.spaceMember;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class ModifyMemberResponse extends BaseResponse {

    private ModifyMemberResponse(Boolean success, String msg) {
        super(success, msg);
    }

    public static ModifyMemberResponse of(Boolean success, String msg){
        return new ModifyMemberResponse(success, msg);
    }

    public static ResponseEntity<ModifyMemberResponse> newResponse(SuccessCode code){
        return new  ResponseEntity(ModifyMemberResponse.of(true, code.getMsg()), code.getStatus());
    }
}
