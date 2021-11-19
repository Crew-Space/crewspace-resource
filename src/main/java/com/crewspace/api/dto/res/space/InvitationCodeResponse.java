package com.crewspace.api.dto.res.space;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class InvitationCodeResponse extends BaseResponse {

    private InvitationCodeResponseDTO data;

    private InvitationCodeResponse(Boolean success, String msg, InvitationCodeResponseDTO data) {
        super(success, msg);
        this.data = data;
    }

    public static ResponseEntity<InvitationCodeResponse> toResponse(SuccessCode code, InvitationCodeResponseDTO data){
        InvitationCodeResponse response = new InvitationCodeResponse(true, code.getMsg(), data);
        return new ResponseEntity(response, code.getStatus());
    }

}
