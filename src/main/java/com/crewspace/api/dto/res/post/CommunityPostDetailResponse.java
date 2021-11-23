package com.crewspace.api.dto.res.post;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class CommunityPostDetailResponse extends BaseResponse {
    CommunityPostDetailResponseDTO data;

    private CommunityPostDetailResponse(Boolean success, String msg,
        CommunityPostDetailResponseDTO data) {
        super(success, msg);
        this.data = data;
    }

    public static CommunityPostDetailResponse of(Boolean success, String msg,
        CommunityPostDetailResponseDTO data) {
        return new CommunityPostDetailResponse(success, msg, data);
    }

    public static ResponseEntity<CommunityPostDetailResponse> newResponse(SuccessCode code, CommunityPostDetailResponseDTO data){
        CommunityPostDetailResponse response = CommunityPostDetailResponse.of(true, code.getMsg(), data);
        return new ResponseEntity(response, code.getStatus());
    }
}
