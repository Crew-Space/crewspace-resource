package com.crewspace.api.dto.res.space;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class SpaceMainResponse extends BaseResponse {

    private SpaceMainResponseDTO data;

    private SpaceMainResponse(Boolean success, String msg,
        SpaceMainResponseDTO data) {
        super(success, msg);
        this.data = data;
    }

    public static ResponseEntity<SpaceMainResponse> newResponse(SuccessCode code, SpaceMainResponseDTO data){
        SpaceMainResponse response = new SpaceMainResponse(true, code.getMsg(), data);
        return new ResponseEntity(response, code.getStatus());
    }

}
