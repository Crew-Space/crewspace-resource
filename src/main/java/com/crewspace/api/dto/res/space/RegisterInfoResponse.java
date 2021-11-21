package com.crewspace.api.dto.res.space;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class RegisterInfoResponse extends BaseResponse {
    private RegisterInfoResponseDTO data;

    private RegisterInfoResponse(Boolean success, String msg, RegisterInfoResponseDTO data) {
        super(success, msg);
        this.data = data;
    }

    public static ResponseEntity<RegisterInfoResponse> toResponse(SuccessCode code, RegisterInfoResponseDTO data){
        RegisterInfoResponse response = new RegisterInfoResponse(true, code.getMsg(), data);
        return new ResponseEntity(response, code.getStatus());
    }
}
