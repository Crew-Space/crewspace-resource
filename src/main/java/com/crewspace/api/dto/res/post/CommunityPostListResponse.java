package com.crewspace.api.dto.res.post;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class CommunityPostListResponse extends BaseResponse {

    private CommunityPostListResponseDTO data;

    private CommunityPostListResponse(Boolean success, String msg,
        CommunityPostListResponseDTO data) {
        super(success, msg);
        this.data = data;
    }

    public static ResponseEntity<CommunityPostListResponse> newResponse(SuccessCode code, CommunityPostListResponseDTO data){
        CommunityPostListResponse response = new CommunityPostListResponse(true, code.getMsg(), data);
        return new ResponseEntity(response, code.getStatus());

    }
}
