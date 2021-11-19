package com.crewspace.api.dto.res;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class CreateSpaceResponse extends BaseResponse {

    private CreateSpaceResponseDTO data;

    private CreateSpaceResponse(Boolean success, String msg, CreateSpaceResponseDTO data) {
        super(success, msg);
        this.data = data;
    }

    public static ResponseEntity<CreateSpaceResponse> toResponse(SuccessCode code, CreateSpaceResponseDTO data){
        CreateSpaceResponse response = new CreateSpaceResponse(true, code.getMsg(), data);
        return new ResponseEntity(response, code.getStatus());
    }
}
