package com.crewspace.api.dto.res.space;


import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class SpaceEnterResponse extends BaseResponse {

    private SpaceEnterResponseDTO data;

    private SpaceEnterResponse(Boolean success, String msg,
        SpaceEnterResponseDTO data) {
        super(success, msg);
        this.data = data;
    }

    public static ResponseEntity<SpaceEnterResponse> toResponse(SuccessCode code, SpaceEnterResponseDTO data){
        SpaceEnterResponse response = new SpaceEnterResponse(true, code.getMsg(), data);
        return new ResponseEntity(response, code.getStatus());
    }
}
