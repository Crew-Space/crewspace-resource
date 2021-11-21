package com.crewspace.api.dto.res.spaceMember;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class EnteredSpaceResponse extends BaseResponse {
    private EnteredSpaceResponseDTO data;

    private EnteredSpaceResponse(Boolean success, String msg, EnteredSpaceResponseDTO data) {
        super(success, msg);
        this.data = data;
    }

    public static ResponseEntity<EnteredSpaceResponse> toResponse(SuccessCode code, EnteredSpaceResponseDTO data){
        EnteredSpaceResponse response = new EnteredSpaceResponse(true, code.getMsg(), data);
        return new ResponseEntity(response, code.getStatus());
    }
}
