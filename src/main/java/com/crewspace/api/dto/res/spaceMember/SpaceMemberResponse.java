package com.crewspace.api.dto.res.spaceMember;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class SpaceMemberResponse extends BaseResponse {

    private SpaceMemberResponseDTO data;

    private SpaceMemberResponse(Boolean success, String msg, SpaceMemberResponseDTO data) {
        super(success, msg);
        this.data = data;
    }

    public static ResponseEntity<SpaceMemberResponse> newResponse(SuccessCode code, SpaceMemberResponseDTO data){
        SpaceMemberResponse response = new SpaceMemberResponse(true, code.getMsg(), data);
        return new ResponseEntity(response, code.getStatus());
    }

}
