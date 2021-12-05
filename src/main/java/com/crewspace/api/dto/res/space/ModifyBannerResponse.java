package com.crewspace.api.dto.res.space;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.BaseResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class ModifyBannerResponse extends BaseResponse {

    private ModifyBannerResponse (Boolean success, String msg) {
        super(success, msg);
    }

    public static ModifyBannerResponse  of(Boolean success, String msg){
        return new ModifyBannerResponse (success, msg);
    }

    public static ResponseEntity<ModifyBannerResponse> newResponse(SuccessCode code){
        return new  ResponseEntity(ModifyBannerResponse.of(true, code.getMsg()), code.getStatus());
    }
}
