package com.crewspace.api.dto.res.spaceMember;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class SpaceMemberSearchResponse extends BaseResponse {

    SpaceMemberSearchResponseDTO data;

    private SpaceMemberSearchResponse(Boolean success, String msg, SpaceMemberSearchResponseDTO data) {
        super(success, msg);
        this.data = data;
    }

    public static ResponseEntity<SpaceMemberSearchResponse> newResponse(SuccessCode code, SpaceMemberSearchResponseDTO data){
        SpaceMemberSearchResponse response = new SpaceMemberSearchResponse(true, code.getMsg(), data);
        return new ResponseEntity(response, code.getStatus());
    }
}
