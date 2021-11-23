package com.crewspace.api.dto.res.postCategory;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class WriteCommunityResponse extends BaseResponse {

    private WriteCommunityResponse(Boolean success, String msg) {
        super(success, msg);
    }

    public static WriteCommunityResponse of(Boolean success, String msg){
        return new WriteCommunityResponse(success, msg);
    }

    public static ResponseEntity<WriteCommunityResponse> newResponse(SuccessCode code){
        return new  ResponseEntity(WriteCommunityResponse.of(true, code.getMsg()), code.getStatus());
    }
}
