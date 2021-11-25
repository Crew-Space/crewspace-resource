package com.crewspace.api.dto.res.spaceMember;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class SpaceMemberListResponse extends BaseResponse {

    SpaceMemberListResponseDTO data;

    private SpaceMemberListResponse(Boolean success, String msg, SpaceMemberListResponseDTO data) {
        super(success, msg);
        this.data = data;
    }

    public static ResponseEntity<SpaceMemberListResponse> newResponse(SuccessCode code, SpaceMemberListResponseDTO data){
        SpaceMemberListResponse response = new SpaceMemberListResponse(true, code.getMsg(), data);
        return new ResponseEntity(response, code.getStatus());
    }
}
